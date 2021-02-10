package project.n01351778.carlos

import androidx.room.*


@Dao
interface EvaluationDao {

    @Query("SELECT id FROM evaluations WHERE place_name == :placeName")
    fun getId(placeName: String): Int

    @Query("SELECT * FROM evaluations WHERE id == :id")
    fun findById(id: Int): EvaluationEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(evaluation: EvaluationEntity)

    @Query("SELECT * FROM evaluations")
    fun getAll(): List<EvaluationEntity>

    @Query("DELETE FROM evaluations")
    fun deleteAll()

    @Query("SELECT * FROM evaluations WHERE place_name = :placeName")
    fun findByResult(placeName: String): List<EvaluationEntity>

    @Query("DELETE FROM evaluations WHERE place_name = :placeName")
    fun delete(placeName: String): Int

    @Update()
    fun update(evaluation: EvaluationEntity): Int

    @Update
    fun updateEvaluation(evaluation: EvaluationEntity?): Int

    fun updateEvaluation(id: Int, placeName: String, rate: String, comment: String) {
        val evaluation: EvaluationEntity = findById(getId(placeName))
        evaluation.placeName = placeName
        evaluation.placeRate = rate
        evaluation.placeComment = comment
        updateEvaluation(evaluation)
    }

}