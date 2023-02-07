package uz.nurlibaydev.tunetesttask.presentation.cards.card

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.nurlibaydev.tunetesttask.R
import uz.nurlibaydev.tunetesttask.data.models.CardDetail
import uz.nurlibaydev.tunetesttask.databinding.ItemCardBinding

/**
 *  Created by Nurlibay Koshkinbaev on 07/02/2023 23:54
 */

class CardAdapter : ListAdapter<CardDetail, CardAdapter.CardViewHolder>(CardItemCallBack) {

    inner class CardViewHolder(private val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val item = getItem(absoluteAdapterPosition)
            binding.apply {
                tvBankName.text = item.bank_name
                if(item.isMain) tvIsMain.visibility = View.VISIBLE
                tvTotalSum.text = item.total_summa
                tvCardName.text = item.card_name
                tvCardOwner.text = item.owner_name
                tvExpiryDate.text = item.expiry_date
                tvCardNumber.text = item.card_number
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(ItemCardBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)))
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind()
    }

}

object CardItemCallBack : DiffUtil.ItemCallback<CardDetail>() {

    override fun areItemsTheSame(oldItem: CardDetail, newItem: CardDetail): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CardDetail, newItem: CardDetail): Boolean {
        return oldItem.card_name == newItem.card_name && oldItem.bank_name == newItem.bank_name &&
                oldItem.expiry_date == newItem.expiry_date && oldItem.owner_name == newItem.owner_name &&
                oldItem.total_summa == newItem.total_summa && oldItem.isMain == newItem.isMain
    }
}