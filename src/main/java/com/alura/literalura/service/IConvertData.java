package com.alura.literalura.service;

public interface IConvertData {
    <T> T getData(String json, Class<T> tclass);
}
