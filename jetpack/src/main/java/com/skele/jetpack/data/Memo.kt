package com.skele.jetpack.data

import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * ### Entity
 * Entity represents table in Room database
 * - Entity class must have at least one PrimaryKey field ( autoGenerate equals autoIncrement in SQL )
 * - Room uses field name as column name as default, but can be changed with #ColumnInfo()
 * - Table and column name in database are case-insensitive
 *
 * 데이터베이스에서 테이블을 표현하는 Entity.
 * - Entity는 하나의 PrimaryKey를 가져야한다.
 * - 기본적으로 field의 이름을 칼럼 이름으로 사용하지만, #ColumnInfo을 통해 변경할 수 있다.
 * - 테이블과 칼럼 이름은 대소문자를 구분하지 않는다.
 */
@Entity(tableName = "memo")
data class Memo(
    // Use `@ColumnInfo(name = "column name")` to change column name in database.
    // `@Ignore` annotation will exclude that field on column creation and will not persist.
    @PrimaryKey(autoGenerate = true) var id : Long = 0,
    val title : String,
    val content : String,
    val regDate : Long,
    val hasDue : Boolean,
    val dueDate : Long,
){
    // When autoGenerate is set to true on primary key,
    // insert method treats 0 or null as 'not-set' while inserting the item.
    constructor(title: String, content: String, regDate: Long, isUsing: Boolean, dueDate: Long) : this(0, title, content, regDate, isUsing, dueDate)
}
