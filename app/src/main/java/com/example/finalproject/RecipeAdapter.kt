import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.model.Recipe
import com.squareup.picasso.Picasso

class RecipeAdapter() : RecyclerView
    .Adapter<RecipeAdapter
    .RecipeViewHolder>
    () {
    private var recipes: List<Recipe> = ArrayList()
    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recipeTextView: TextView =
            itemView.findViewById(R.id.recipe_title)
        private val recipeImageView: ImageView =
            itemView.findViewById(R.id.recipe_img)

        fun bind(recipe: Recipe) {
            recipeTextView.text = recipe.getTitle()
            if (recipe.getImg() != null) {
                Picasso.get().load(recipe.getImg()).placeholder(R.drawable.default_pic)
                    .into(recipeImageView)
            } else {
                recipeImageView.setImageResource(R.drawable.default_pic)
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
}
