package com.example.finalproj_minor_gr2.works;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class QueryWorksClass extends Worker {
    public QueryWorksClass(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        QueryInBackground();
        return Result.success();
    }

    private void QueryInBackground() {
        Log.i("Query", "Hit");
    }
}
