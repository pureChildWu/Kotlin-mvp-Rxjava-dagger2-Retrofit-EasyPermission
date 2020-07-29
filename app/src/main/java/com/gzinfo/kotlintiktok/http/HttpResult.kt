package com.gzinfo.kotlintiktok.http

/**
 *@ClassName:HttpResult
 *@Author:CreatBy wlh
 *@Time:2020/7/23 17点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
class HttpResult<T> {

    /*  //response
      {
          "code":"0",
              "message":"操作成功！",
              "total":1000, // 返回分页数据时，表示总的数据条数， 如果不是返回分页数据，没有这个字段
              "data":{ //具体的返回
          "xxxx":"xxxx"
      }
      }*/

    /*  //response
      {
          "code":"0",
              "message":"操作成功！",
              "total":1000, // 返回分页数据时，表示总的数据条数， 如果不是返回分页数据，没有这个字段
              "data":{ //具体的返回
          "xxxx":"xxxx"
      }
      }*/
    private lateinit var code: String
    private lateinit var message: String
    private var data: T = TODO()

    fun getCode(): String {
        return code
    }

    fun setCode(code: String) {
        this.code = code
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String) {
        this.message = message
    }

    fun getData(): T {
        return data
    }

    fun setData(data: T) {
        this.data = data
    }
}