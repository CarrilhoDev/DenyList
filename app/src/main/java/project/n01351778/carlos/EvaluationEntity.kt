package project.n01351778.carlos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "evaluations")
data class EvaluationEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long,

    @ColumnInfo(name = "place_name")
    var placeName: String,

    @ColumnInfo(name = "evaluation")
    var placeRate: String,

    @ColumnInfo(name = "comment")
    var placeComment: String
)