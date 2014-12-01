/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatizacion;

import com.serotonin.io.serial.SerialParameters;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.code.RegisterRange;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.msg.ReadCoilsRequest;
import com.serotonin.modbus4j.msg.ReadCoilsResponse;
import com.serotonin.modbus4j.msg.ReadDiscreteInputsRequest;
import com.serotonin.modbus4j.msg.ReadDiscreteInputsResponse;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersRequest;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersResponse;
import com.serotonin.modbus4j.msg.WriteCoilRequest;
import com.serotonin.modbus4j.msg.WriteCoilResponse;
import com.serotonin.modbus4j.msg.WriteRegistersRequest;
import com.serotonin.modbus4j.msg.WriteRegistersResponse;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NORE
 */
public class ModBus {

    private ModbusMaster master;
// MODBUS network slave address  paquete  windows EmbeddedInteliggence
    public final static int SLAVE_ADDRESS = 0;

    // Serial port baud rate
    private final static int BAUD_RATE = 9600;

    private Crixus instance = null;

    public ModBus() {
        SerialParameters serialParameters = new SerialParameters();
        // Set the serial port of the MODBUS communication serialParameters
        serialParameters.
                setCommPortId("COM1");
        // Set no parity

        serialParameters.setParity(0);
        // Set the data bits is 8 bits serialParameters
        serialParameters.
                setDataBits(8);
        // Set to 1 stop bit

        serialParameters.setStopBits(1);

        serialParameters.setPortOwnerName("Numb nuts");
        // Serial port baud rate serialParameters
        serialParameters.
                setBaudRate(BAUD_RATE);

        ModbusFactory modbusFactory = new ModbusFactory();

        master = modbusFactory.createRtuMaster(serialParameters);

        try {
            master.init();

        } catch (Exception e) {

        }

    }

    /**
     *
     * read digital inputs
     */
    public boolean[] readDiscreteInputTest(int slaveId, int start, int len) {

        boolean[] vals = new boolean[6];
        for (int i = start; i < len + 1; i++) {
            try {
                //i is the register number
                vals[i] = (boolean) master.getValue(1, RegisterRange.COIL_STATUS, i, DataType.BINARY);
                System.out.println(i + " VALOR REGISTRO : " + vals[i]); // read coil
            } catch (ModbusTransportException ex) {
                Logger.getLogger(ModBus.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ErrorResponseException ex) {
                Logger.getLogger(ModBus.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return vals;
    }

    /**
     *
     * Read Holding register
     *
     */
    private static void readHoldingRegistersTest(ModbusMaster master, int slaveId, int start, int len) {

        try {

            ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(slaveId, start, len);

            ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) master.send(request);
            if (response.isException()) {
                System.out.println("Exception response: message =" + response.getExceptionMessage());
            } else {
                System.out.println(Arrays.toString(response.getShortData()));
            }

        } catch (ModbusTransportException e) {

            e.printStackTrace();

        }

    }

    /**
     *
     * write data to the holding register
     *
     */
    public static void writeRegistersTest(ModbusMaster master, int slaveId, int start, short[] values) {

        try {

            WriteRegistersRequest request = new WriteRegistersRequest(slaveId, start, values);

            WriteRegistersResponse response = (WriteRegistersResponse) master.send(request);
            if (response.isException()) {
                System.out.println("Exception response: message =" + response.getExceptionMessage());
            } else {
                System.out.println("success");
            }

        } catch (ModbusTransportException e) {

            e.printStackTrace();

        }

    }

    /*
     read coils
     */
    public static void readCoils(ModbusMaster master, int slaveId, int start, int len) {
        try {
            ReadCoilsRequest request = new ReadCoilsRequest(slaveId, start, start);

            ReadCoilsResponse response = (ReadCoilsResponse) master.send(request);

            if (response.isException()) {
                System.out.println("Exception response: message =" + response.getExceptionMessage());
            } else {
                System.out.println("SIZE: " + response.getShortData().length);
                System.out.println(Arrays.toString(response.getBooleanData()));
            }

        } catch (ModbusTransportException e) {

        }
    }

    /**
     *
     * write coils state
     *
     */
    public static void writeCoils(ModbusMaster master, int slaveId, int position, boolean value) {

        try {

            WriteCoilRequest request = new WriteCoilRequest(slaveId, position, value);

            WriteCoilResponse response = (WriteCoilResponse) master.send(request);
            if (response.isException()) {
                System.out.println("Exception response: message =" + response.getExceptionMessage());
            } else {
                System.out.println("success");
            }

        } catch (ModbusTransportException e) {

            e.printStackTrace();

        }

    }

    public void close() {
        master.destroy();
    }

    public void writeCommand(int registro, boolean value) {
        System.out.println("escribiendo registro al plc");
        try {
            master.setValue(SLAVE_ADDRESS, RegisterRange.COIL_STATUS, registro, DataType.BINARY, value); // change coils state
        } catch (ModbusTransportException ex) {
            Logger.getLogger(ModBus.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ErrorResponseException ex) {
            Logger.getLogger(ModBus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ModbusMaster getMaster() {
        return master;
    }

    public void writeContador(int intValue) {
        try {
            master.setValue(SLAVE_ADDRESS, RegisterRange.HOLDING_REGISTER, 0, DataType.TWO_BYTE_INT_UNSIGNED, intValue); // change Holding register value
        } catch (ModbusTransportException ex) {
            Logger.getLogger(ModBus.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ErrorResponseException ex) {
            Logger.getLogger(ModBus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
