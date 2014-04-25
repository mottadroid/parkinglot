package com.door3.parkinglot;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

public class ParkingLot {

    private ParkingSpot[] spots;

    private EnumMap<ParkingSpot.Type, Integer> emptySpotsCount = new EnumMap<ParkingSpot.Type, Integer>(ParkingSpot.Type.class);

    private static final int MAX_VALID_SIZE = 100000;

    public static ParkingLot newRandom(int spotsCount, int handicappedPer100Spots) {
        validateSpotsCount(spotsCount);
        validateHandicapped(handicappedPer100Spots);
        ParkingLot lot = new ParkingLot(spotsCount);
        lot.fillRandom(handicappedPer100Spots);
        return lot;
    }

    private static void validateHandicapped(int handicappedPer100Spots) {
        if (handicappedPer100Spots < 0 || handicappedPer100Spots > 100) {
            throw new RuntimeException("Handicapped places amount per 100 total spots can't be " + handicappedPer100Spots + "; it must be between 0 and 100");
        }
    }

    private static void validateSpotsCount(int spotsCount) {
        if (spotsCount < 1 || spotsCount > MAX_VALID_SIZE) {
            throw new RuntimeException("Parkings spots count " + spotsCount + " is not allowed. Should be between 1 and " + MAX_VALID_SIZE);
        }
    }

    private ParkingLot(int spotsCount) {
        spots = new ParkingSpot[spotsCount];
        for (ParkingSpot.Type type : ParkingSpot.Type.values()) {
            emptySpotsCount.put(type, 0);
        }
    }

    private void fillRandom(int handicappedPer100Spots) {
        int N = spots.length;
        for (int i = 0; i < N; i++) {
            boolean empty = getRandomEmpty();
            ParkingSpot.Type type = getRandomType(handicappedPer100Spots);
            spots[i] = new ParkingSpot(empty, type);
            if (empty) emptySpotsCount.put(type, emptySpotsCount.get(type) + 1);
        }
    }

    private static boolean getRandomEmpty() {
        Random r = new Random();
        return r.nextInt() % 2 == 0;
    }

    private static ParkingSpot.Type getRandomType(int handicappedPer100Spots) {
        Random r = new Random();
        int i = r.nextInt(100);
        if (i <= handicappedPer100Spots) return ParkingSpot.Type.HANDICAPPED;
        return ParkingSpot.Type.STANDART;
    }

    /**
     * Returns true if park successfull, false otherwise
     *
     * @param spotPosition
     * @param driverType
     * @return
     */
    public boolean park(int spotPosition, ParkingSpot.Type driverType) {
        if (canPark(spotPosition, driverType)) {
            spots[spotPosition].setEmpty(false);
            ParkingSpot.Type spotType = spots[spotPosition].getType();
            emptySpotsCount.put(spotType, emptySpotsCount.get(spotType) - 1);
            return true;
        }
        return false;
    }

    private boolean canPark(int spotPosition, ParkingSpot.Type driverType) {
        if (!spots[spotPosition].isEmpty()) return false;
        if (spots[spotPosition].getType().equals(ParkingSpot.Type.HANDICAPPED) && !driverType.equals(ParkingSpot.Type.HANDICAPPED))
            return false;
        return true;
    }

    /**
     * Returns true if remove successfull, false otherwise
     *
     * @param spotPosition
     * @return
     */
    public boolean remove(int spotPosition) {
        if (!spots[spotPosition].isEmpty()) {
            spots[spotPosition].setEmpty(true);
            ParkingSpot.Type spotType = spots[spotPosition].getType();
            emptySpotsCount.put(spotType, emptySpotsCount.get(spotType) + 1);
            return true;
        }
        return false;
    }

    public int emptySpotsCountTotal() {
        int totalEmpty = 0;
        for (Map.Entry<ParkingSpot.Type, Integer> entry : emptySpotsCount.entrySet()) {
            totalEmpty = totalEmpty + entry.getValue();
        }
        return totalEmpty;
    }

    public int emptySpotsCount(ParkingSpot.Type spotType) {
        return emptySpotsCount.get(spotType);
    }

    public ParkingSpot[] getSpots() {
        return spots;
    }


}
