package com.netty;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;

import javax.xml.bind.DatatypeConverter;


public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    //客户端注册
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);

    }



    //通道建立连接后的响应
    @Override
    public void channelActive(ChannelHandlerContext ctx) {

        String signal = ctx.channel().remoteAddress().toString();
        NettyCommon.nettyChannelManage.addChannel(signal, (SocketChannel)ctx.channel());
        System.out.println("建立连接：" + ctx.channel().remoteAddress().toString());
         String reply = "你好" + ctx.channel().remoteAddress().toString()+"\n";
             ctx.writeAndFlush(Unpooled.copiedBuffer(reply.getBytes()));
    }

    // 客户端主动断开服务端的链接后的回调函数
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String signal = ctx.channel().remoteAddress().toString();
        NettyCommon.nettyChannelManage.removeChannel(signal);
        System.out.println("连接关闭：" + ctx.channel().remoteAddress().toString());
    }


    /*
     * @说明:该方法用于接收从客户端接收的信息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        //ChannelHandlerContext提供各种不同的操作用于触发不同的I/O时间和操作
        //只调用write是不会释放的，它会缓存，直到调用flush
       // DeviceSession session = ctx.channel().attr(KEY).get();     // 检测是否自己注册的客户端

       // ctx.writeAndFlush(msg);
        ByteBuf buffer=(ByteBuf) msg;
       // if (buffer == null||session == null) {
       //     closeConnection(ctx); // 关闭连接
       // }
        byte[] req = new byte[buffer.readableBytes()];
        buffer.readBytes(req);
       // String body = new String(req, "UTF-8");
      //  System.out.println("接收客户端"+ctx.channel().remoteAddress().toString()+"的数据:" + body);
        System.out.println("接收数据"+ctx.channel().remoteAddress().toString()+"的数据:"+
                DatatypeConverter.printHexBinary(req));

    }

    //channelRead执行后触发
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
       //
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
       // cause.printStackTrace();
        System.out.println("网络连接异常");
        ctx.close();
    }
}