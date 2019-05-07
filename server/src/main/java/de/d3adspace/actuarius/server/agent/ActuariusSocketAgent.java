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

package de.d3adspace.actuarius.server.agent;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Felix Klauke <info@felix-klauke.de>
 */
public class ActuariusSocketAgent implements IActuariusAgent {

    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;
    private final Class<? extends ServerChannel> serverChannelClass;
    private final ChannelInitializer<Channel> channelInitializer;
    private Channel channel;

    @Inject
    public ActuariusSocketAgent(@Named("bossGroup") EventLoopGroup bossGroup, @Named("workerGroup") EventLoopGroup workerGroup, Class<? extends ServerChannel> serverChannelClass, ChannelInitializer<Channel> channelInitializer) {
        this.bossGroup = bossGroup;
        this.workerGroup = workerGroup;
        this.serverChannelClass = serverChannelClass;
        this.channelInitializer = channelInitializer;
    }

    @Override
    public void bootstrap() {
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(serverChannelClass)
                    .childHandler(channelInitializer);

            channel = serverBootstrap.bind(53).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isRunning() {
        return channel != null && channel.isOpen();
    }

    @Override
    public void stop() {

        if (channel == null) {
            return;
        }

        channel.close();
    }
}
