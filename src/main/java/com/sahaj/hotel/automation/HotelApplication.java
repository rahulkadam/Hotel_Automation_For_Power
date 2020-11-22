package com.sahaj.hotel.automation;

import com.sahaj.hotel.automation.dto.SensorInputDto;
import com.sahaj.hotel.automation.entity.Hotel;
import com.sahaj.hotel.automation.entity.hotelenum.CorridorType;
import com.sahaj.hotel.automation.service.HotelInputService;
import com.sahaj.hotel.automation.service.SensorMovementService;
import com.sahaj.hotel.automation.service.ShowOutputService;

import java.io.File;
import java.util.Scanner;

public class HotelApplication {

    private static HotelInputService hotelInputService;
    private static SensorMovementService sensorMovementService;
    private static ShowOutputService showOutputService;

    public static void main(String args[]) {
        System.out.println("*************** Hotel Automation *************************");
        intializeObject();
        runViaInputFile();
    }

    /**
     * Read input via Input file and run the Program
     */
    public static void runViaInputFile() {
        try {
            File myObj = new File("./src/main/resources/input.txt");
            Scanner myReader = new Scanner(myObj);
            String acceptFloor = myReader.nextLine();
            String acceptMainCorridor = myReader.nextLine();
            String acceptSubCorridor = myReader.nextLine();

            System.out.println(acceptFloor);
            System.out.println(acceptMainCorridor);
            System.out.println(acceptSubCorridor);

            int floorCount = getFloorCount(acceptFloor);
            int mainCorridorCount = getCorridorCount(acceptMainCorridor);
            int subCorridorCount = getCorridorCount(acceptSubCorridor);

            Hotel hotel = hotelInputService.initializeHotel(floorCount);
            hotel = hotelInputService.acceptMainCorridorPerFloor(mainCorridorCount, hotel);
            hotel = hotelInputService.acceptSubCorridorPerFloor(subCorridorCount, hotel);
            System.out.println("*****************Default State *******************");
            showOutputService.printHotelState(hotel);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println("\nAction : " + data);
                SensorInputDto sensorInputDto = readSensorInput(data);
                if (sensorInputDto.isMovement()) {
                    hotel =  sensorMovementService.recordSensorMovement(hotel, sensorInputDto);
                } else {
                    hotel =  sensorMovementService.recordNoSensorMovement(hotel, sensorInputDto);
                }
                showOutputService.printHotelState(hotel);
                Thread.sleep(1000);
            }
            myReader.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Read signal from sensor
     * @param input
     * @return
     */
    public static SensorInputDto readSensorInput(String input) {
        String str[] = input.split(":");
        String movement = str[0];
        String floorNumber = str[2];
        String corridorType = str[3];
        String corridorNumber = str[4];

        SensorInputDto sensorInputDto = new SensorInputDto();

        if ("movement".equalsIgnoreCase(movement.trim())) {
            sensorInputDto.setMovement(true);
        } else {
            sensorInputDto.setMovement(false);
        }

        if ("Subcorridor".equalsIgnoreCase(corridorType.trim())) {
            sensorInputDto.setType(CorridorType.SUB);
        } else {
            sensorInputDto.setType(CorridorType.MAIN);
        }

        sensorInputDto.setFloorNumber(Integer.parseInt(floorNumber.trim()));
        sensorInputDto.setCorridorNumber(Integer.parseInt(corridorNumber.trim()));
        return sensorInputDto;
    }


    /**
     * Read floor count
     * @param line
     * @return
     */
    public static int getFloorCount(String line) {
        String floorCounts[] = line.split(":");
        return Integer.parseInt(floorCounts[1].trim());
    }

    public static int getCorridorCount(String line) {
        String corridorCounts[] = line.split(":");
        return Integer.parseInt(corridorCounts[1].trim());
    }

    public static void intializeObject() {
        hotelInputService = new HotelInputService();
        sensorMovementService = new SensorMovementService();
        showOutputService = new ShowOutputService();
    }

    public static void runViaHardCodeInput() {
        Hotel hotel = hotelInputService.initializeHotel(2);
        hotel = hotelInputService.acceptMainCorridorPerFloor(1, hotel);
        hotel = hotelInputService.acceptSubCorridorPerFloor(2, hotel);
        System.out.println("Default State ");
        showOutputService.printHotelState(hotel);
        // hotel =  sensorMovementService.recordSensorMovement(hotel, 1, CorridorType.SUB, 1);
        System.out.println("\n\nMovement on floor " + 1);
        showOutputService.printHotelState(hotel);
        // sensorMovementService.recordNoSensorMovement(hotel, 1, CorridorType.SUB, 1);
        System.out.println("\n\nNo Movement on floor " + 1);
        showOutputService.printHotelState(hotel);
    }


}
