package io.github.zornx5.interfaces.facade.rest;
/*
RESTful 资源

1. GET
 安全、幂等；安全就是指不影响服务器的数据，幂等是指同一个请求发送多次返回的结果应该相同
 用于获取资源；

2. HEAD 
 安全、幂等；
 与 get 方法类似，但不返回 message body 内容，仅仅是获得获取资源的部分信息（content-type、content-length）；
 restful 框架中较少使用；

3. POST 
 非安全、非幂等；
 用于创建子资源；

4. PUT 
 非安全、幂等；
 用于创建、更新资源；

5. DELETE 
 非安全、幂等；
 删除资源；
 delete 请求一般返回 3 种码
    200（OK）——删除成功，同时返回已经删除的资源。
    202（Accepted）——删除请求已经接受，但没有被立即执行（资源也许已经被转移到了待删除区域）。
    204（No Content）——删除请求已经被执行，但是没有返回资源（也许是请求删除不存在的资源造成的）

6. OPTIONS 
 安全、幂等；
 用于 url 验证，验证接口服务是否正常；

7. TRACE
 安全、幂等；
 维基百科: "回显服务器收到的请求，这样客户端可以看到（如果有）哪一些改变或者添加已经被中间服务器实现。"
 restful 框架中较少使用

8. PATCH 
 非安全、幂等；
 用于创建、更新资源，于PUT类似，区别在于PATCH代表部分更新；
 后来提出的接口方法，使用时可能去要验证客户端和服务端是否支持；

主意：

1. post 和 put 的区别：
 post 和 put 的区别在于 uri，再有 post 是非幂等的：
   post 用于创建子资源，比如接口：POST /api/person/ 会创建一个资源比如 /api/person/1 或者 /api/person/2 ... 或者/api/person/n，创建了新的 uri；
   put 创建资源的 uri 是 PUT /api/person/1，这样就创建了一个资源. 如果 1 已经存在那么就是更新. 所以 put 并不是只是更新操作。
 通常情况下，将 post、get、put、delete 对应到 CRUD 操作上，但实际上 put 并不是只能更新。

2. patch 的使用：
 patch 是 2010 后成为的正式 http 方法. 详见 RFC5789. 它是对 put 的补充；
 在没有 patch 之前. 用 put 进行更新操作，对于接口中通常会有一个逻辑规则，如：如果对象的的一个字符属性为 NULL. 那么就是不更新该属性（字段）值，如果对象的字符属性是 ""，那么就更新该属性（字段）的值，通过这种方式来避免全部覆盖的操作。
 有了 patch 就解决了这种判断，在 put 接口中不管属性是不是 null. 都进行更新，在 patch 接口中就对非 null 的进行更新。
*/