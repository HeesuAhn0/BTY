package com.example.betterthanyesterday.Viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.betterthanyesterday.Repository.RecipeRepository


data class Recipe(
    val title: String = "",
    val time: String? = null,
    val calories: Int = 0,
    val ingredients: String = "",
    val description: String? = null,
    val imageUrl: String = ""
)

class RecipeViewModel : ViewModel() {

    private val recipeRepository = RecipeRepository()

    private val _recipes = MutableLiveData<MutableList<Recipe>>(mutableListOf())
    val recipes: LiveData<MutableList<Recipe>> get() = _recipes

    init {
        recipeRepository.observeRecipe(_recipes)
    }
    fun addRecipe(recipe: Recipe) {
        recipeRepository.postRecipe(recipe)
    }

    fun deleteRecipe(recipe: Recipe) {
        _recipes.value?.remove(recipe)
        _recipes.value = _recipes.value

        recipeRepository.deleteRecipeFromDatabase(recipe)

    }

    fun updateRecipe(recipe: Recipe) {
        recipeRepository.updateRecipeInDatabase(recipe)
    }

    fun addRecipeWithImage(recipe: Recipe, imageUri: Uri) {
        recipeRepository.uploadImageToFirebase(imageUri,
            onSuccess = { imageUrl ->
                val updatedRecipe = recipe.copy(imageUrl = imageUrl)

                addRecipe(updatedRecipe)

                _recipes.value = _recipes.value?.map {
                    if (it.title == recipe.title) updatedRecipe else it
                }?.toMutableList()
            },
            onFailure = { e ->
                Log.e("RecipeViewModel", "Image upload failed: ${e.message}")
            })
    }
    fun uploadImageAndSaveRecipe(recipe: Recipe, imageUri: Uri) {
        recipeRepository.uploadImageToFirebase(
            imageUri,
            onSuccess = { imageUrl ->
                val updatedRecipe = recipe.copy(imageUrl = imageUrl)
                updateRecipe(updatedRecipe)
            },
            onFailure = { e ->
                Log.e("RecipeViewModel", "Image upload failed: ${e.message}")
            }
        )
    }

}