package com.kylix.submissionbajp3.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.kylix.submissionbajp3.model.local.entity.MovieModel
import com.kylix.submissionbajp3.model.repository.DataRepository
import com.kylix.submissionbajp3.utils.Resource

class MovieViewModel(private  val dataRepository: DataRepository): ViewModel() {
    val movies : LiveData<List<MovieModel>> = dataRepository.getMovieList()
    fun movieDetail (id : Int): LiveData<MovieModel> = dataRepository.getMovieById(id)

    fun insertMovies(movies: List<MovieModel>) {
        dataRepository.insertMovies(movies)
    }

    val getMovies: LiveData<Resource<List<MovieModel>>> = dataRepository.getMovies()

    val getMoviesPaged: LiveData<Resource<PagedList<MovieModel>>> = dataRepository.getMovieAsPaged()

    fun setFavoriteMovie() {
        getMovie.value?.data?.let {
            val newState = !it.favorite
            dataRepository.setFavoriteMovie(it, newState)
        }
    }

    val movieId = MutableLiveData<Int>()

    fun setMovieId(movieId: Int){
        this.movieId.value = movieId
    }

    val getMovie: LiveData<Resource<MovieModel>> = Transformations.switchMap(movieId) { movieId ->
        dataRepository.getMovie(movieId)
    }


}