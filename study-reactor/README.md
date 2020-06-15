# Study Reactor

## subscribe 订阅
reator 与 stream方式不同

Reator -> push

Stream -> pull

// 5个参数，但是第5个基本使用不上
// 4个参数都可以默认为空

stream(param1, param2, param3, param4)

param1: 数据
param2: 处理错误的机制
param3: 订阅完后做相应的处理
param4: 订阅数据的量，默认是Long.MAX_VALUE