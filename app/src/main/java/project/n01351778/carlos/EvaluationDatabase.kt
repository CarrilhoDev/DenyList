package project.n01351778.carlos

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(entities = [EvaluationEntity::class], version = 1)
abstract class EvaluationDatabase : RoomDatabase() {
    abstract fun evaluationDao(): EvaluationDao

    companion object {

        @Volatile
        private var INSTANCE: EvaluationDatabase? = null

        fun getInstance(context: Context): EvaluationDatabase {
            if (INSTANCE == null) {
                synchronized(EvaluationDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        EvaluationDatabase::class.java,
                        "evaluations.db"
                    ).allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE as EvaluationDatabase
        }
    }
}