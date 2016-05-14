//  Copyright 2016 The Sawdust Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package concurrent;

import java.util.Stack;

import static java.lang.Thread.sleep;

/**
 * 2 stacks work as a queue
 * more consumers
 * more producers
 * make the running time of get be O(1). blocking when there is no message.
 */
public class WaitAndNotify {
    public static void main(String args[]) {
        final int FULL_SIZE = 2;
        final Stack in = new Stack();
        final Stack out = new Stack();
        final Object monitor = new Object();
        final Object lockAllGet = new Object();
        final Object lockAllPut = new Object();

        Runnable put = new Runnable() {
            private void move() {
                synchronized (in) {
                    synchronized (out) {
                        while (!in.empty()) {
                            out.push(in.pop());
                        }
                        System.out.println("moved data");
                    }
                }
            }

            private void putMessage() throws InterruptedException {
                synchronized (lockAllPut) {
                    if (in.size() == FULL_SIZE) {
                        // wait till condition is met
                        while (in.size() == FULL_SIZE) {
                            synchronized (monitor) {

                                monitor.wait();
                            }
                            move();
                        }
                    }

                    synchronized (in) {
                        in.push(new java.util.Date().toString());
                        System.out.println("added");
                    }
                }
            }

            @Override
            public void run() {
                try {
                    while (true) {
                        putMessage();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable get = new Runnable() {
            private String getMessage() throws InterruptedException {
                synchronized (lockAllGet) {
                    if (out.empty()) {
                        while (out.empty()) {
                            synchronized (monitor) {
                                monitor.notify();
                            }
                            sleep(10l);
                        }
                    }

                    String message = (String) out.peek();
                    out.pop();
                    System.out.println("get");
                    return message;
                }
            }

            @Override
            public void run() {
                try {
                    while (true) {
                        getMessage();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(put).start();
        new Thread(put).start();

        new Thread(get).start();
        new Thread(get).start();
        new Thread(get).start();
    }
}
