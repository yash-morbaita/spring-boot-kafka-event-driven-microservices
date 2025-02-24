package com.codewithyash.orderservice.service;

import com.codewithyash.basedomain.dto.OrderEvent;
import com.codewithyash.basedomain.dto.OrderEventResponse;
import com.codewithyash.orderservice.tasks.IRunnableTask;
import com.codewithyash.orderservice.tasks.SendNewOrderEventTask;
import com.codewithyash.orderservice.tasks.ValidateInputParametersRequestTask;
import com.codewithyash.orderservice.utility.TaskEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;

@Service
public class TaskExecutorService {

    private static final Logger logger = LoggerFactory.getLogger(TaskExecutorService.class);

    private final Map<TaskEnum, IRunnableTask> taskMap;

    public TaskExecutorService(SendNewOrderEventTask sendNewOrderEventTask, ValidateInputParametersRequestTask validateInputParametersRequestTask) {
        this.taskMap = Map.of(
                TaskEnum.VALIDATEINPUTPARAMETERSREQUEST, validateInputParametersRequestTask,
                TaskEnum.SENDNEWORDEREVENT, sendNewOrderEventTask
        );
    }


    public OrderEventResponse executeTask(List<TaskEnum> tasks, OrderEvent orderEvent) {
        StringJoiner stringJoiner = new StringJoiner("|");
        stringJoiner.add("Starting TaskExecutor Service");

        OrderEventResponse  orderEventResponse = new OrderEventResponse();
        List<CompletableFuture<?>> futures = new ArrayList<>();

        stringJoiner.add("Tasks:");
        for(TaskEnum task: tasks) {
            stringJoiner.add(task.toString());
            IRunnableTask iRunnableTask = taskMap.get(task);
            if(task == null) {
                throw new RuntimeException("Task Not found" + task.toString());
            }
            futures.add(execute(iRunnableTask,orderEvent,orderEventResponse));
            stringJoiner.add(task.toString() + ": Completed");
        }

        // Wait for all tasks to complete
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();


        logger.info(stringJoiner.toString());

        return orderEventResponse;
    }

    @Async("taskExecutor")
    public CompletableFuture<?> execute(IRunnableTask task, OrderEvent orderEvent, OrderEventResponse orderEventResponse) {
        return task.execute(orderEvent,orderEventResponse);
    }
}
