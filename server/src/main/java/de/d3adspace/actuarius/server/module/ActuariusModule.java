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

package de.d3adspace.actuarius.server.module;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import de.d3adspace.actuarius.server.ActuariusConstants;
import de.d3adspace.actuarius.server.ActuariusServerImpl;
import de.d3adspace.actuarius.server.IActuariusServer;
import de.d3adspace.actuarius.server.agent.ActuariusDatagramAgent;
import de.d3adspace.actuarius.server.agent.ActuariusSocketAgent;
import de.d3adspace.actuarius.server.agent.IActuariusAgent;
import de.d3adspace.actuarius.server.initializer.ActuariusChannelInitializer;
import de.d3adspace.actuarius.server.initializer.ActuariusDatagramChannelInitializer;
import de.d3adspace.actuarius.server.provider.BossGroupProvider;
import de.d3adspace.actuarius.server.provider.WorkerGroupProvider;
import de.d3adspace.actuarius.server.query.IQueryManager;
import de.d3adspace.actuarius.server.query.QueryManagerImpl;
import de.d3adspace.actuarius.server.thread.BossThreadFactory;
import de.d3adspace.actuarius.server.thread.WorkerThreadFactory;
import de.d3adspace.actuarius.server.utils.NettyUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.socket.DatagramChannel;

import java.util.concurrent.ThreadFactory;

/**
 * @author Felix Klauke <info@felix-klauke.de>
 */
public class ActuariusModule extends AbstractModule {

    @Override
    protected void configure() {
        bindConstant().annotatedWith(Names.named("bossGroupThreadCount")).to(ActuariusConstants.BOSS_GROUP_THREAD_COUNT);
        bindConstant().annotatedWith(Names.named("workerGroupThreadCount")).to(ActuariusConstants.WORKER_GROUP_THREAD_COUNT);
        bindConstant().annotatedWith(Names.named("bossGroupThreadNamePrefix")).to(ActuariusConstants.BOSS_GROUP_PREFIX);
        bindConstant().annotatedWith(Names.named("workerGroupThreadNamePrefix")).to(ActuariusConstants.WORKER_GROUP_PREFIX);

        bind(new TypeLiteral<Class<? extends ServerChannel>>() {
        }).toInstance(NettyUtils.getServerChannelClass());

        bind(new TypeLiteral<Class<? extends DatagramChannel>>() {
        }).toInstance(NettyUtils.getServerDatagramChannelClass());

        bind(ThreadFactory.class).annotatedWith(Names.named("bossGroupThreadFactory")).to(BossThreadFactory.class);
        bind(ThreadFactory.class).annotatedWith(Names.named("workerGroupThreadFactory")).to(WorkerThreadFactory.class);

        bind(EventLoopGroup.class).annotatedWith(Names.named("bossGroup")).toProvider(BossGroupProvider.class);
        bind(EventLoopGroup.class).annotatedWith(Names.named("workerGroup")).toProvider(WorkerGroupProvider.class);

        bind(new TypeLiteral<ChannelInitializer<Channel>>() {
        }).to(ActuariusChannelInitializer.class);

        bind(new TypeLiteral<ChannelInitializer<DatagramChannel>>() {
        }).to(ActuariusDatagramChannelInitializer.class);

        bind(IQueryManager.class).to(QueryManagerImpl.class);

        bind(IActuariusAgent.class).annotatedWith(Names.named("datagramAgent")).to(ActuariusDatagramAgent.class);
        bind(IActuariusAgent.class).annotatedWith(Names.named("socketAgent")).to(ActuariusSocketAgent.class);

        bind(IActuariusServer.class).to(ActuariusServerImpl.class);
    }
}
