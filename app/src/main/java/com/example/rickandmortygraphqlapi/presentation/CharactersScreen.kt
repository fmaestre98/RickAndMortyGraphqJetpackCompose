package com.example.rickandmortygraphqlapi.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.rickandmortygraphqlapi.R
import com.example.rickandmortygraphqlapi.domain.SimpleCharacter

@Composable
fun CharactersScreen(
    characters: LazyPagingItems<SimpleCharacter>,
    state: CharacterViewModel.CharactersState,
    onSelectedCharacter: (id: String) -> Unit,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = characters.loadState) {
        if (characters.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (characters.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            )
        }
    }
    Box(modifier = modifier.fillMaxSize()) {
        if (characters.loadState.refresh is LoadState.Loading) {
            LoadingScreen()
        }else if (state.selectedCharacter != null) {
            CharacterDetails(detailsCharacter = state.selectedCharacter)
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(characters) { character ->
                   if (character!=null){
                       CharacterItem(
                           simpleCharacter = character,
                           modifier = Modifier
                               .fillMaxWidth()
                               .padding(16.dp)
                               .clickable { onSelectedCharacter(character?.id!!) }
                       )
                   }

                }
                item {
                    if (characters.loadState.append is LoadState.Loading){
                        Row(modifier= Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            CircularProgressIndicator(modifier = Modifier)
                        }

                    }
                }
            }
        }

    }
    /*Box(modifier = modifier.fillMaxSize()) {
        if (state.isLoading) {
            LoadingScreen()
        } else if (state.error) {
            ErrorScreen(retryAction = retryAction)
        } else if (state.selectedCharacter != null) {
            CharacterDetails(detailsCharacter = state.selectedCharacter)
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.characters) { character ->
                    CharacterItem(
                        simpleCharacter = character,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable { onSelectedCharacter(character?.id!!) }
                    )
                }
            }
        }

    }*/

}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.loading)
        )
    }
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.loading_failed))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
private fun CharacterItem(modifier: Modifier = Modifier, simpleCharacter: SimpleCharacter?) {
    Card(
        modifier = modifier.background(Color.LightGray),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box() {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .aspectRatio(1f),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(simpleCharacter?.image)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.image_description),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentScale = ContentScale.Fit
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    color = Color.Black,
                    style = MaterialTheme.typography.displayMedium,
                    text = simpleCharacter?.name.toString()
                )
            }

        }
    }
}