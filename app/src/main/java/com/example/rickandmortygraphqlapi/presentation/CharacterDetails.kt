package com.example.rickandmortygraphqlapi.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.rickandmortygraphqlapi.R
import com.example.rickandmortygraphqlapi.domain.DetailsCharacter

@Composable
fun CharacterDetails(
    modifier: Modifier = Modifier,
    detailsCharacter: DetailsCharacter,
    onBackHandler: () -> () -> Unit
) {
   BackHandler() {
       onBackHandler()
   }
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier
            .background(MaterialTheme.colorScheme.secondary)
            .wrapContentSize()
            .padding(10.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(10.dp)),
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(detailsCharacter?.image)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.image_description),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img)
        )
        Column(
            horizontalAlignment = Alignment.Start, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .scrollable(scrollState, Orientation.Vertical)
        ) {
            Text(text = "Name: " + detailsCharacter.name)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Status: " + detailsCharacter.status)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Gender: " + detailsCharacter.gender)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Species: " + detailsCharacter.species)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Location: " + detailsCharacter.location)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Origin: " + detailsCharacter.origin)
            Spacer(modifier = Modifier.height(16.dp))

        }

    }
}