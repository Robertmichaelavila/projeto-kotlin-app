package com.example.projetorobert

import android.content.Context
//import android.os.VibrationEffect
import android.content.Intent
//import android.os.Vibrator
import androidx.work.Worker
import androidx.work.WorkerParameters

class CalculationWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val num1 = inputData.getDouble("NUM1", 0.0)
        val num2 = inputData.getDouble("NUM2", 0.0)
        val sum = num1 + num2

        // Ativar a vibração
//        val vibrator = applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
//        if (vibrator.hasVibrator()) {
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
//            } else {
//                vibrator.vibrate(500)
//            }
//        }

        // Inserir o cálculo no banco de dados
        val dbHelper = DatabaseHelper(applicationContext)
        dbHelper.insertCalculation("$num1 + $num2", "$sum")

        // Enviar o resultado de volta
        val resultIntent = Intent("com.example.projetorobert.RESULT")
        resultIntent.putExtra("RESULT", "Result: $sum")
        applicationContext.sendBroadcast(resultIntent)

        return Result.success()
    }
}
