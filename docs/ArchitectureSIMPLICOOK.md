
## Architecture Diagram
![ArchitectureDiagram_I1](Architecture_Diagram.png){width=70% height=70%}

# Presentation Layer

### AddRecipeActivity
- Responsible for adding and editing a recipe

### HomePageActivity
- The starting view for the app

### RecipeAdapterActivity
- Responsible for displaying recipes in the home page

### RecipeDisplayAdapter
- Responsible for displaying detailed information about a recipe

### PlanListActivity
- Responsible for displaying planning recipes

# Persistence
### RecipePersistenceStub
- A temporary database for the app

### RecipePersistence 
- The interface for the recipe in the database

### RecipePersistenceHSQLDB
- Responsible for interacting with the recipe HSQLDB

### PlanningPersistence 
- The interface for planning recipe in the database

### PlanningPersistenceHSQLDB
- Responsible for interacting with the planning recipe HSQLDB

# Domain Specific Object
### Ingredient
- Ingredient Object
### Recipe
- Recipe Object

### Planning
- Planning Object

# Business
### RecipeManagement
- The main class that handles creating, deleting and editing recipes

### SearchManagement
- The main class that handles searching for recipes

### Services
- Manages the instantiaon of the persistence of the recipes

### ActionHelpler
- Helps retrieve,add, edit and delete recipes

### SortRecipe
- Responsible for the logic of sorting recipes by differing categories

### ISortRecipe
- The interface for sorting recipes

### PlanningManagement
- Responsible for the retrieval and managemment of recipes and ingredients.

### RecipeValidator
- Responsible for ensuring new recipes are valid

## Utilities
### IdGenerator
- Creates unique ids for recipes

### DatabaseUtilities
- Converts recipes from the database into recipe and planning objects

### IngredientUtils
- Encodes and decodes ingredients to strings and ingredient objects





