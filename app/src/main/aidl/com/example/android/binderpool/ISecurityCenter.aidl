package com.example.android.binderpool;

interface ISecurityCenter {
    String encrypt(String content);
    String decrypt(String password);
}