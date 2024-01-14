/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.dsm.controllerArduino;

import com.fazecast.jSerialComm.SerialPort;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
/**
 *
 * @author aquat
 */
public class ConexionArduino {
    SerialPort puertoSerial;
    OutputStream salida; //TX
    InputStream entrada; //RX
    
    public void conexion(String puerto, int vel){
        try {
            //se obtiene el puerto COM asignado
            puertoSerial=SerialPort.getCommPort(puerto);
            //se configura la velocidad de transmision de datos
            puertoSerial.setBaudRate(vel);
            //comunicacion es en serie el envio es de 8bits
            puertoSerial.setNumDataBits(8);
            //tomando en cuenta el bit de start y stop
            puertoSerial.setNumStopBits(0);
            //se aplica el bit de paridad para garantizar
            //que se envio y recibio correctamente
            puertoSerial.setParity(0);
            //se abre el puerto COM
            puertoSerial.openPort();
        } catch (Exception e) {
            System.out.println("Revisa tu conexión con el puerto serial");
        }
    }
    
    public void busDatos(){
    
        salida = puertoSerial.getOutputStream(); 
        entrada = puertoSerial.getInputStream();
    }
    
    public void enviarDatosArduino(String c) throws IOException{
    
        salida.write(c.getBytes());//se realiza el envio de paquetes con validacion de bit de paridad
        salida.flush();//limpiamos el bus de datos 
    }
    
    public String recibeArduino() throws IOException{
    String datos = "";
        while (entrada.available()>0) {            
            datos += (char)entrada.read();
        }
        return datos;
    }
    
    public boolean cerrarPuerto(){
    boolean cierra;
    cierra = puertoSerial.closePort();
    return cierra;
    }
}

