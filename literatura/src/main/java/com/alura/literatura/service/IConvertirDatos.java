package com.alura.literatura.service;

public interface IConvertirDatos {
    // Este es el "contrato" que tu clase ConvertirDatos debe cumplir
    <T> T obtenerDados(String json, Class<T> clase);
}