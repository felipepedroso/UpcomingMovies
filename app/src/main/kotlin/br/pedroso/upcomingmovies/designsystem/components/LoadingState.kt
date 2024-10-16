package br.pedroso.upcomingmovies.designsystem.components

import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.pedroso.upcomingmovies.R

@Composable
fun LoadingState(
    modifier: Modifier = Modifier,
    text: String = stringResource(id = R.string.loading_state_text),
) {
    Row(
        modifier = modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = spacedBy(16.dp, Alignment.CenterHorizontally),
    ) {
        CircularProgressIndicator()
        Text(text = text, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingStatePreview() {
    LoadingState()
}
