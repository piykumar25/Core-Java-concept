import java.util.*;
import java.util.concurrent.*;

public class OpticalPathPlanner {

    static class ServiceRequest {
        int id;
        String source;
        String destination;
        int bandwidth;

        ServiceRequest(int id, String source, String destination, int bandwidth) {
            this.id = id;
            this.source = source;
            this.destination = destination;
            this.bandwidth = bandwidth;
        }
    }

    static class PathResult {
        int requestId;
        String path;
        boolean isSuccess;
        String message;

        PathResult(int requestId, String path, boolean isSuccess, String message) {
            this.requestId = requestId;
            this.path = path;
            this.isSuccess = isSuccess;
            this.message = message;
        }

        @Override
        public String toString() {
            return "Request " + requestId + ": Path = " + path + ", Success = " + isSuccess + ", Message = " + message;
        }
    }

    // Simulated resource validator
    public static boolean validateResources(String path, int bandwidth) {
        // Simulate external resource manager validation (e.g., checking bandwidth availability)
        return new Random().nextBoolean();
    }

    // Simulated method to compute a path between source and destination
    public static PathResult computeAndValidatePath(ServiceRequest request) {
        try {
            // Simulate computation or external API call delay
            Thread.sleep(200 + new Random().nextInt(300));
            String path = request.source + " -> " + request.destination;

            // Simulate validation with Optical Resource Manager (ORM)
            boolean valid = validateResources(path, request.bandwidth);

            if (valid) {
                return new PathResult(request.id, path, true, "Path allocated successfully.");
            } else {
                return new PathResult(request.id, path, false, "Insufficient bandwidth or resource conflict.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new PathResult(request.id, "", false, "Thread was interrupted");
        }
    }

    public static List<PathResult> processRequests(List<ServiceRequest> requests) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Future<PathResult>> futures = new ArrayList<>();

        for (ServiceRequest request : requests) {
            futures.add(executor.submit(() -> computeAndValidatePath(request)));
        }

        List<PathResult> results = new ArrayList<>();
        for (Future<PathResult> future : futures) {
            results.add(future.get());
        }

        executor.shutdown();
        return results;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        List<ServiceRequest> requests = Arrays.asList(
                new ServiceRequest(1, "NodeA", "NodeB", 10),
                new ServiceRequest(2, "NodeC", "NodeD", 20),
                new ServiceRequest(3, "NodeE", "NodeF", 15),
                new ServiceRequest(4, "NodeG", "NodeH", 25),
                new ServiceRequest(5, "NodeI", "NodeJ", 30),
                new ServiceRequest(6, "NodeK", "NodeL", 12)
        );

        List<PathResult> results = processRequests(requests);

        for (PathResult result : results) {
            System.out.println(result);
        }
    }
}
