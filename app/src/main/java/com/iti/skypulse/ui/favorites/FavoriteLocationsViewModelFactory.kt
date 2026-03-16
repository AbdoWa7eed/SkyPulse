import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iti.skypulse.di.ServiceLocator
import com.iti.skypulse.ui.favorites.FavoriteLocationsViewModel

class FavoriteLocationsViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteLocationsViewModel( ServiceLocator.weatherRepository) as T
    }
}