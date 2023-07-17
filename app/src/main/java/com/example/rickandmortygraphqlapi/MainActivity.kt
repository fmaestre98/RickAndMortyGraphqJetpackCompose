package com.example.rickandmortygraphqlapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmortygraphqlapi.presentation.CharacterViewModel
import com.example.rickandmortygraphqlapi.presentation.CharactersScreen
import com.example.rickandmortygraphqlapi.ui.theme.RickAndMortyGraphQLApiTheme
import com.example.rickandmortygraphqlapi.ui.theme.utlis.PolygonShape
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyGraphQLApiTheme {
                val viewModel = hiltViewModel<CharacterViewModel>()
                val state by viewModel.state.collectAsState()
                val charactersFlow = viewModel.characterPagerFlow.collectAsLazyPagingItems()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.secondary
                ) {
                    Scaffold(
                        topBar = {
                            topAppBar(
                                showArrow = state.selectedCharacter != null,
                                onBackPressed = viewModel::removeSelectedCharacter
                            )
                        }
                    ) { innerPadding ->
                        CharactersScreen(
                            characters = charactersFlow,
                            state = state,
                            onSelectedCharacter = viewModel::selectCharacter,
                            onBackAction = {viewModel::removeSelectedCharacter},
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topAppBar(showArrow: Boolean = false, onBackPressed: () -> Unit = {}) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        title = {
            Text(
                text = "Rick and Morty",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

        },
        navigationIcon = {
            IconButton(onClick = {
                if (showArrow) {
                    onBackPressed()
                }
            }) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Arrow Back",
                    tint = if (showArrow) Color.Black else Color.Transparent
                )

            }
        })
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TopAppBarPreview() {
    RickAndMortyGraphQLApiTheme {
        // topAppBar(true)
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.secondary
        ) {
            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(-110.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.55f)
                            .height(200.dp)
                            .clip(PolygonShape(8, 0f))
                            .background(Color.Red)
                    ){
                        Image(painter = painterResource(id = R.drawable.rick), contentDescription = null, modifier = Modifier.fillMaxSize())
                    }
                }
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.55f)
                            .height(210.dp)
                            .clip(PolygonShape(6, 0f))
                            .background(Color.Red)
                    )
                }

            }

        }
    }
}
