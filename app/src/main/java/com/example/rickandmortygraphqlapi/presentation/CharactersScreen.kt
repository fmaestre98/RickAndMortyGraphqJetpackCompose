package com.example.rickandmortygraphqlapi.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.rickandmortygraphqlapi.R
import com.example.rickandmortygraphqlapi.domain.SimpleCharacter
import com.example.rickandmortygraphqlapi.ui.theme.utlis.PolygonShape

@Composable
fun CharactersScreen(
    characters: LazyPagingItems<SimpleCharacter>,
    state: CharacterViewModel.CharactersState,
    onSelectedCharacter: (id: String) -> Unit,
    modifier: Modifier = Modifier,
    onBackAction: () -> Unit
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
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.secondary)
            .fillMaxSize()
    ) {
        if (characters.loadState.refresh is LoadState.Loading) {
            LoadingScreen()
        } else if (state.selectedCharacter != null) {
            CharacterDetails(detailsCharacter = state.selectedCharacter) { onBackAction }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 5.dp, start = 2.dp, end = 2.dp),
                verticalArrangement = Arrangement.spacedBy(-98.dp)
            ) {
                val itemModifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
                    .clip(PolygonShape(6, 0f))
                itemsIndexed(characters) { index, character ->
                    if (character != null) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = if (index % 2 == 0) Alignment.CenterStart else Alignment.CenterEnd
                        ) {
                            CharacterItem(simpleCharacter = character,
                                modifier = itemModifier.clickable { onSelectedCharacter(character?.id!!) })
                        }

                    }

                }
                item {
                    if (characters.loadState.append is LoadState.Loading) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(modifier = Modifier)
                        }

                    }
                }
            }
        }

    }

}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()
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

    Box(modifier = modifier) {

        AsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(simpleCharacter?.image).crossfade(true).build(),
            contentDescription = stringResource(R.string.image_description),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentScale = ContentScale.FillBounds
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        ) {
            Text(
                color = Color.Black,
                text = simpleCharacter?.name.toString(),
                fontSize = 10.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

    }

}

