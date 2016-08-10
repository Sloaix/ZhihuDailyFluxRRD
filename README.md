# 知乎日报
本示例基于Flux架构,使用Retrofit RxJava Dagger2 [Apollo](https://github.com/lsxiao/Apollo) (Compile-time RxBus)构建。

## Flux架构

![](https://github.com/lsxiao/ZhihuDailyRRD/blob/master/screenshot/flux_flow.png?raw=true)


## 工程结构

![](https://github.com/lsxiao/ZhihuDailyRRD/blob/master/screenshot/structure.jpg?raw=true)

## 示例预览

![](https://github.com/lsxiao/ZhihuDailyRRD/blob/master/demo.gif?raw=true)

## 关于Flux中View的UI交互处理问题
考虑如下问题,从Activity_1点击按钮，跳转到Activity_2,此时按钮的点击是不是也要经过action->dispatcher->store->view这样的流动？

在我实践过后，我个人建议与业务相关的UI交互尽量走Flux的Action分发（例如点击按钮从服务器获取数据），纯粹的UI逻辑可以直接在View(这里指Flux中的View，例如Activity)中处理，例如页面跳转。

## 关于Dispatcher
Dispatcher在Flux中只应存在一个。

本示例中的Dispatcher只是简单的负责注册，取消注册，分发action，并没有实现通过调整回调方法的触发次序来管理Store之间的依赖关系，Store可以声明等待其他Store更新完毕再更新自己,也就是没有实现waitFor方法(以后会实现，因为需要恰当的使用场景)。

本示例中的Dispatcher中有关Store的注册与取消注册采用了订阅/取消模式，使用了第三方RxBus库[Apollo](https://github.com/lsxiao/Apollo)来实现。

##API

本示例中的所有Api，都是通过[izzyleung](https://github.com/izzyleung)的分析而来，最终解释权归知乎所有。

## 开源许可

Apache License Version 2.0
