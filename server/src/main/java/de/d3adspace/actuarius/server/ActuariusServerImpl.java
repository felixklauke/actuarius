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

package de.d3adspace.actuarius.server;

import de.d3adspace.actuarius.server.agent.IActuariusAgent;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Felix Klauke <info@felix-klauke.de>
 */
public class ActuariusServerImpl implements IActuariusServer {

    private final IActuariusAgent datagramAgent;
    private final IActuariusAgent socketAgent;

    @Inject
    public ActuariusServerImpl(@Named("datagramAgent") IActuariusAgent datagramAgent, @Named("socketAgent") IActuariusAgent socketAgent) {
        this.datagramAgent = datagramAgent;
        this.socketAgent = socketAgent;
    }

    @Override
    public void start() {
        datagramAgent.bootstrap();
        socketAgent.bootstrap();
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return datagramAgent.isRunning() && socketAgent.isRunning();
    }
}
