package com.graduation_work.bonappetit.test_util

import com.graduation_work.bonappetit.data.repository.StockRepositoryImpl

class StockRepoTest(
	private val stockRepo: StockRepositoryImpl
) {
/*
    init {
        runBlocking {
            Log.d("db_test", "Initialize food table.")

            foodRepo.deleteAll()

            val food1 = FoodEntity.create4Insert("キュウリ", "個")
            val food2 = FoodEntity.create4Insert("ジュンサイ", "グラム")
            val food3 = FoodEntity.create4Insert("ネギ", "本")

            foodRepo.register(food1)
            foodRepo.register(food2)
            foodRepo.register(food3)
            val records = foodRepo.getAll()
            Log.d("db_test", "${records.size} records were registered.")
        }
    }

    fun startTest() {

        runBlocking {
            Log.d("db_test", "Test started.")

            stockRepo.deleteAll()
            /*
             * 在庫登録画面から在庫を登録する場合を想定
             * 本番ではFoodEntityのリストを持ってるはずなのでそこからfood_idとってStockEntityを作る
             */
            val newStock1 = StockEntity.create4Insert(
                foodId = foodRepo.getByName("キュウリ").id,
                count = 3,
                limit = LocalDate.parse("20221005", DateTimeFormatter.BASIC_ISO_DATE),
                bestOrExpiry = true
            )
            Log.d("db_test", "check point1")
            val newStock2 = StockEntity.create4Insert(
                foodId = foodRepo.getByName("ジュンサイ").id,
                count = 20
            )

            stockRepo.register(newStock1)
            Log.d("db_test", "check point2")
            stockRepo.register(newStock2)
            Log.d("db_test", "check point3")

            var records = stockRepo.getAll()

            Log.d("db_test", "${records.size} records were registered in stock table.")

            records.map {
                Log.d("db_test", """
                    |${it.foodName}:${it.count}${it.unit},
                    |limit:${it.limit}
                """.trimMargin())
            }
        }
    }
    
 */
}