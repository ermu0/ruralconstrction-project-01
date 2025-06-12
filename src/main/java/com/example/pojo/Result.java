package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code;
    private String msg;
    private Object data;

    //无数据正确响应返回
    public static Result success() {
        return new Result(1, "success", null);
    }

    //有数据正确响应返回
    public static Result success(Object data) {
        return new Result(1, "success", data);
    }

    //返回报错
    public static Result error(String msg) {
        return new Result(0, "error", msg);
    }
}
