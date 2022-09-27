package com.graduation_work.bonappetit.test_util

import android.util.Log
import com.graduation_work.bonappetit.database.entities.FoodEntity
import com.graduation_work.bonappetit.repository.FoodRepository
import kotlinx.coroutines.runBlocking

//テスト用クラス
class FoodRepoTest(
    private val foodRepo: FoodRepository
) {
    private val sampleFood1 = FoodEntity.create4Insert("キュウリ", "個")
    private val sampleFood2 = FoodEntity.create4Insert("ジュンサイ", "g")
    private val sampleFood3 = FoodEntity.create4Insert("ネギ", "本")

    fun startTest() {

        runBlocking {   // runBlockingはテストでの使用を目的としているためCoroutineの中で呼び出すべきではない
            Log.d("db_test", "Test started.")

            foodRepo.register(sampleFood1)
            foodRepo.register(sampleFood2)
            foodRepo.register(sampleFood3)

            var records = foodRepo.getAll()
            Log.d("db_test", "${records.size} samples were registered.")

            foodRepo.update(sampleFood2.copy(unit = "グラム"))
            val record = foodRepo.getByName(sampleFood2.name)
            Log.d("db_test", "${sampleFood2.name} was updated. : $record")

            foodRepo.delete(sampleFood1)
            records = foodRepo.getAll()
            Log.d("db_test", "${sampleFood1.name} was deleted. : $records")

            foodRepo.deleteAll()
            records = foodRepo.getAll()
            Log.d("db_test", "Test completed. All sample records were deleted. : $records")
        }
    }
}