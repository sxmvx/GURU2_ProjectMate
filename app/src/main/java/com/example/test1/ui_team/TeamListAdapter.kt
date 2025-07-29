// TeamListAdapter.kt
package com.example.test1.ui_team

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test1.R

class TeamListAdapter(
    private val teams: List<Team>,
    private val onItemClick: (Team) -> Unit
) : RecyclerView.Adapter<TeamListAdapter.TeamViewHolder>() {

    inner class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTeamName: TextView = itemView.findViewById(R.id.textViewTeamName)
        val textViewRole: TextView = itemView.findViewById(R.id.textViewRole)
        val viewDot: View = itemView.findViewById(R.id.viewTeamColor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_team, parent, false)
        return TeamViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val team = teams[position]
        holder.textViewTeamName.text = team.name
        holder.textViewRole.text = team.role
        holder.viewDot.setBackgroundColor(Color.parseColor(getColorCode(team.color)))
        holder.itemView.setOnClickListener { onItemClick(team) }
    }

    override fun getItemCount(): Int = teams.size

    private fun getColorCode(colorName: String): String = when (colorName) {
        "pink" -> "#F8B5B5"
        "gray" -> "#CCCCCC"
        "blue" -> "#A4D8F0"
        "purple" -> "#CBA4F0"
        else -> "#AAAAAA"
    }
}
