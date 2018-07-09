package com.framgia.demo.jdk9;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class ProcessApiDemo {

    public static void main(String[] args) throws IOException {
        observeProcessInfo();
        //spawnNewOne();
        //enumerateAllProcesses();
        //psAuxGrepJava();
    }

    private static void observeProcessInfo() {
        ProcessHandle ph = ProcessHandle.current();
        System.out.println(ph.info());
        System.out.println("PID: " + ph.pid());
        System.out.println("User: " + ph.info().user().orElse("Unknown"));
        System.out.println("CPU time so far: " + ph.info().totalCpuDuration().orElse(Duration.ZERO));
    }

    private static void spawnNewOne() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("java", "-version");
        Process process = processBuilder.inheritIO().start();
        ProcessHandle ph = process.toHandle();
        System.out.println(ph.info());
    }

    private static void enumerateAllProcesses() {
        Stream<ProcessHandle> liveProcesses = ProcessHandle.allProcesses();
        liveProcesses.filter(ProcessHandle::isAlive)
                .forEach(ph -> System.out.println(ph.pid()));
    }

    private static void psAuxGrepJava() throws IOException {
        ProcessBuilder psAux = new ProcessBuilder("ps", "aux");
        ProcessBuilder grepJava = new ProcessBuilder("grep", "java");
        List<Process> processes = ProcessBuilder.startPipeline(asList(psAux, grepJava));
        processes.forEach(p -> {
            try {
                System.out.println(new String(p.getInputStream().readAllBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
