import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentMusicalBinding
import com.example.finalproject.model.Recipe
import com.example.finalproject.views.RecipesListFragment
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt


class RecipeAdapter() : RecyclerView
.Adapter<RecipeAdapter
.RecipeViewHolder>
    () {
    private var recipes: List<Recipe> = ArrayList()
    var binding: FragmentMusicalBinding? = null
    private var listener: OnRecipeClickListener? = null


    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recipeTextView: TextView =
            itemView.findViewById(R.id.recipe_title)
        private val recipeImageView: ImageView =
            itemView.findViewById(R.id.recipe_img)
        private val caloriesTextView: TextView =
            itemView.findViewById(R.id.recipe_calories)

        fun bind(recipe: Recipe) {
            recipeTextView.text = recipe.title
            caloriesTextView.text =
                "Calories: " + (recipe.calories?.toFloatOrNull()?.roundToInt()
                    ?: 0)
            if (recipe.img != null) {
                Picasso.get().load(recipe.img).placeholder(R.drawable.default_pic)
                    .into(recipeImageView)
            } else {
                recipeImageView.setImageResource(R.drawable.default_pic)
            }
            itemView.setOnClickListener {
                listener?.onRecipeClick(recipe)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_musical_row, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    fun submitList(newList: List<Recipe>) {
        val diffCallback = RecipeDiffCallback(recipes, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        recipes = newList
        diffResult.dispatchUpdatesTo(this)
    }


    class RecipeDiffCallback(
        private val oldList: List<Recipe>,
        private val newList: List<Recipe>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(
            oldItemPosition: Int,
            newItemPosition: Int
        ): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun areContentsTheSame(
            oldItemPosition: Int,
            newItemPosition: Int
        ): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

    interface OnRecipeClickListener {
        fun onRecipeClick(recipe: Recipe)
    }

    fun setOnRecipeClickListener(listener: RecipesListFragment) {
        this.listener = listener
    }
}

