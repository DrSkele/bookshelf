package com.skele.jetpack.main.list

import androidx.recyclerview.widget.DiffUtil
import com.skele.jetpack.data.Memo

/**
 * ### Comparator
 * Item comparator for list in recycler view
 * - Adapter compares list submitted with #subList() with old list using this comparator and updates the change.
 * - First, items are compared if it has same identifier.
 * - Second, if two items have the same identifier, checks content of those items.
 * - If items does not match, the recycler view considers items as changed.
 *
 * Recycler view에 사용되는 비교기
 * - Adapter가 submitList()를 통해 입력받은 목록와 이전의 목록을 비교해 변경점을 반영한다.
 * - 첫번째로 두 객체가 같은 식별자를 가지는지 확인한다.
 * - 두번째로 같은 식별자를 가졌다면 객체의 구성요소를 비교한다.
 * - 두 과정에서 하나라도 같지 않다면 변경이 되었다고 판단한다.
 */
object MemoComparator : DiffUtil.ItemCallback<Memo>() {
    override fun areItemsTheSame(oldItem: Memo, newItem: Memo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Memo, newItem: Memo): Boolean {
        return oldItem == newItem
    }
}