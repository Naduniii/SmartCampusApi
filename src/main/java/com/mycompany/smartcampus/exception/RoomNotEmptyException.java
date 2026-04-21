/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus.exception;

/**
 *
 * @author naduniameesha
 */

public class RoomNotEmptyException extends RuntimeException {
    public RoomNotEmptyException(String errorMsg) {
        super(errorMsg);
    }
}
