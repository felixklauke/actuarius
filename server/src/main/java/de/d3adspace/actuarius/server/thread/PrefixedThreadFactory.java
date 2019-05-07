/*
 * MIT License
 *
 * Copyright (c) 2017 - 2019 Felix Klauke
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.d3adspace.actuarius.server.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class PrefixedThreadFactory implements ThreadFactory {

    /**
     * Count for the threads.
     */
    private final AtomicInteger atomicInteger;

    /**
     * Prefix of the thread's names.
     */
    private final String prefix;

    /**
     * Create a new prefixed thread factory with a default count starting at 0.
     *
     * @param prefix The prefix of the thread's names.
     */
    public PrefixedThreadFactory(String prefix) {
        this(new AtomicInteger(0), prefix);
    }

    /**
     * Create a new prefixed thread factory by all its data.
     *
     * @param atomicInteger The thread count.
     * @param prefix        The prefix of the thread's names.
     */
    public PrefixedThreadFactory(AtomicInteger atomicInteger, String prefix) {
        this.atomicInteger = atomicInteger;
        this.prefix = prefix;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setName(prefix + atomicInteger.getAndIncrement());
        return thread;
    }
}
