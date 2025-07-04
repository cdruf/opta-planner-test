package com.cdruf.cloudbalancing;

import lombok.Getter;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class CloudBalancingGenerator {

    private Random random;

    @Getter
    private static class Price {

        private final int hardwareValue;
        private final String description;
        private final int cost;

        private Price(int hardwareValue, String description, int cost) {
            this.hardwareValue = hardwareValue;
            this.description = description;
            this.cost = cost;
        }

    }

    private static final Price[] CPU_POWER_PRICES = { // in gigahertz
            new Price(3, "single core 3ghz", 110), //
            new Price(4, "dual core 2ghz", 140), //
            new Price(6, "dual core 3ghz", 180), //
            new Price(8, "quad core 2ghz", 270), //
            new Price(12, "quad core 3ghz", 400), //
            new Price(16, "quad " + "core " + "4ghz", 1000), //
            new Price(24, "eight core 3ghz", 3000),};
    private static final Price[] MEMORY_PRICES = { // in gigabyte RAM
            new Price(2, "2 gigabyte", 140), //
            new Price(4, "4 gigabyte", 180), //
            new Price(8, "8 gigabyte", 220), //
            new Price(16, "16 gigabyte", 300), //
            new Price(32, "32 gigabyte", 400), //
            new Price(64, "64 gigabyte", 600), //
            new Price(96, "96 gigabyte", 1000),};
    private static final Price[] NETWORK_BANDWIDTH_PRICES = { // in gigabyte per hour
            new Price(2, "2 gigabyte", 100), //
            new Price(4, "4 gigabyte", 200), //
            new Price(6, "6 gigabyte", 300), //
            new Price(8, "8 gigabyte", 400), //
            new Price(12, "12 gigabyte", 600), //
            new Price(16, "16 gigabyte", 800), //
            new Price(20, "20 gigabyte", 1000),};

    private static final int MAXIMUM_REQUIRED_CPU_POWER = 12; // in gigahertz
    private static final int MAXIMUM_REQUIRED_MEMORY = 32; // in gigabyte RAM
    private static final int MAXIMUM_REQUIRED_NETWORK_BANDWIDTH = 12; // in gigabyte per hour


    public CloudBalance createCloudBalance(int computerListSize, int processListSize) {
        random = new Random(47);
        List<Computer> computerList = createComputerList(computerListSize);
        List<Process> processList = createProcessList(processListSize);
        CloudBalance cloudBalance = new CloudBalance(computerList, processList);
        assureComputerCapacityTotalAtLeastProcessRequiredTotal(cloudBalance);
        BigInteger possibleSolutionSize = BigInteger.valueOf(cloudBalance.getComputerList().size())
                                                    .pow(cloudBalance.getProcessList().size());
        System.out.printf("Possible solution size (%s)%n", possibleSolutionSize);
        return cloudBalance;
    }

    private List<Computer> createComputerList(int computerListSize) {
        List<Computer> computerList = new ArrayList<>(computerListSize);
        for (int i = 0; i < computerListSize; i++) {
            Computer computer = generateComputer(i);
            computerList.add(computer);
        }
        return computerList;
    }

    public Computer generateComputer(long id) {
        int cpuPowerPricesIndex = random.nextInt(CPU_POWER_PRICES.length);
        int memoryPricesIndex = distortIndex(cpuPowerPricesIndex, MEMORY_PRICES.length);
        int networkBandwidthPricesIndex = distortIndex(cpuPowerPricesIndex, NETWORK_BANDWIDTH_PRICES.length);
        int cost =
                CPU_POWER_PRICES[cpuPowerPricesIndex].getCost() + MEMORY_PRICES[memoryPricesIndex].getCost() + NETWORK_BANDWIDTH_PRICES[networkBandwidthPricesIndex].getCost();
        Computer computer = new Computer(id, CPU_POWER_PRICES[cpuPowerPricesIndex].getHardwareValue(),
                MEMORY_PRICES[memoryPricesIndex].getHardwareValue(),
                NETWORK_BANDWIDTH_PRICES[networkBandwidthPricesIndex].getHardwareValue(), cost);
        System.out.printf("Created computer with cpuPowerPricesIndex (%s), memoryPricesIndex (%s), " +
                "networkBandwidthPricesIndex (%s).", cpuPowerPricesIndex, memoryPricesIndex,
                networkBandwidthPricesIndex);
        return computer;
    }

    private int distortIndex(int referenceIndex, int length) {
        int index = referenceIndex;
        double randomDouble = random.nextDouble();
        double loweringThreshold = 0.25;
        while (randomDouble < loweringThreshold && index >= 1) {
            index--;
            loweringThreshold *= 0.10;
        }
        double heighteningThreshold = 0.75;
        while (randomDouble >= heighteningThreshold && index <= (length - 2)) {
            index++;
            heighteningThreshold = (1.0 - ((1.0 - heighteningThreshold) * 0.10));
        }
        return index;
    }

    private List<Process> createProcessList(int processListSize) {
        List<Process> processList = new ArrayList<>(processListSize);
        for (int i = 0; i < processListSize; i++) {
            Process process = generateProcess(i);
            processList.add(process);
        }
        return processList;
    }

    public Process generateProcess(long id) {
        int requiredCpuPower = generateRandom(MAXIMUM_REQUIRED_CPU_POWER);
        int requiredMemory = generateRandom(MAXIMUM_REQUIRED_MEMORY);
        int requiredNetworkBandwidth = generateRandom(MAXIMUM_REQUIRED_NETWORK_BANDWIDTH);
        Process process = new Process(id, requiredCpuPower, requiredMemory, requiredNetworkBandwidth, null);
        System.out.printf("Created Process with requiredCpuPower (%d), requiredMemory (%d), requiredNetworkBandwidth "
                + "(%d)%n", requiredCpuPower, requiredMemory, requiredNetworkBandwidth);
        return process;
    }

    private int generateRandom(int maximumValue) {
        double randomDouble = random.nextDouble();
        double parabolaBase = 2000.0;
        double parabolaRandomDouble = (Math.pow(parabolaBase, randomDouble) - 1.0) / (parabolaBase - 1.0);
        if (parabolaRandomDouble < 0.0 || parabolaRandomDouble >= 1.0) {
            throw new IllegalArgumentException("Invalid generated parabolaRandomDouble (" + parabolaRandomDouble + ")");
        }
        int value = ((int) Math.floor(parabolaRandomDouble * maximumValue)) + 1;
        if (value < 1 || value > maximumValue) {
            throw new IllegalArgumentException("Invalid generated value (" + value + ")");
        }
        return value;
    }

    private void assureComputerCapacityTotalAtLeastProcessRequiredTotal(CloudBalance cloudBalance) {
        List<Computer> computerList = cloudBalance.getComputerList();
        int cpuPowerTotal = 0;
        int memoryTotal = 0;
        int networkBandwidthTotal = 0;
        for (Computer computer : computerList) {
            cpuPowerTotal += computer.getCpuPower();
            memoryTotal += computer.getMemory();
            networkBandwidthTotal += computer.getNetworkBandwidth();
        }
        int requiredCpuPowerTotal = 0;
        int requiredMemoryTotal = 0;
        int requiredNetworkBandwidthTotal = 0;
        for (Process process : cloudBalance.getProcessList()) {
            requiredCpuPowerTotal += process.getRequiredCpuPower();
            requiredMemoryTotal += process.getRequiredMemory();
            requiredNetworkBandwidthTotal += process.getRequiredNetworkBandwidth();
        }
        int cpuPowerLacking = requiredCpuPowerTotal - cpuPowerTotal;
        while (cpuPowerLacking > 0) {
            Computer computer = computerList.get(random.nextInt(computerList.size()));
            int upgrade = determineUpgrade(cpuPowerLacking);
            computer.setCpuPower(computer.getCpuPower() + upgrade);
            cpuPowerLacking -= upgrade;
        }
        int memoryLacking = requiredMemoryTotal - memoryTotal;
        while (memoryLacking > 0) {
            Computer computer = computerList.get(random.nextInt(computerList.size()));
            int upgrade = determineUpgrade(memoryLacking);
            computer.setMemory(computer.getMemory() + upgrade);
            memoryLacking -= upgrade;
        }
        int networkBandwidthLacking = requiredNetworkBandwidthTotal - networkBandwidthTotal;
        while (networkBandwidthLacking > 0) {
            Computer computer = computerList.get(random.nextInt(computerList.size()));
            int upgrade = determineUpgrade(networkBandwidthLacking);
            computer.setNetworkBandwidth(computer.getNetworkBandwidth() + upgrade);
            networkBandwidthLacking -= upgrade;
        }
    }

    private int determineUpgrade(int lacking) {
        for (int upgrade : new int[]{8, 4, 2, 1}) {
            if (lacking >= upgrade) {
                return upgrade;
            }
        }
        throw new IllegalStateException("Lacking (" + lacking + ") should be at least 1.");
    }

}
