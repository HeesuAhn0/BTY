package com.example.betterthanyesterday.View.Recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.betterthanyesterday.R
import com.example.betterthanyesterday.Viewmodel.Recipe



class RecipeAdapter(
    private var recipes: MutableList<Recipe>,
    private val onDelete: (Recipe) -> Unit)
    : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {
    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipeTitle: TextView = view.findViewById(R.id.recipe_title)
        val recipeImage: ImageView = view.findViewById(R.id.img_recipe)
        val deleteButton: Button = view.findViewById(R.id.btn_delete)
        val editButton: Button = view.findViewById(R.id.btn_edit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]

        holder.recipeTitle.text = recipe.title
        holder.recipeImage.setImageResource(R.drawable.food)
        holder.deleteButton.setOnClickListener {
            onDelete(recipe)
        }
        holder.recipeTitle.text = recipe.title
        Glide.with(holder.itemView.context)
            .load(recipe.imageUrl)
            .placeholder(R.drawable.food)
            .into(holder.recipeImage)

        holder.editButton.setOnClickListener {
            val context = holder.itemView.context
            if (context is FragmentActivity) {
                val fragmentManager = context.supportFragmentManager

                val dialogFragment = EditRecipeDialogFragment(recipe) { updatedRecipe ->
                    recipes[position] = updatedRecipe
                    notifyItemChanged(position)
                }

                // Show the dialog fragment
                dialogFragment.show(fragmentManager, "EditRecipeDialogFragment")
            }
        }
    }

    override fun getItemCount(): Int = recipes.size

    fun updateRecipes(newRecipes: List<Recipe>) {
        recipes.clear()
        recipes.addAll(newRecipes)
        notifyDataSetChanged()
    }
}