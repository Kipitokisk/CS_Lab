// Function to handle the frequency analysis of the text
document.getElementById('analyzeButton').addEventListener('click', async () => {
    const text = document.getElementById('inputText').value;

    if (!text.trim()) {
        alert('Please enter some text.');
        return;
    }

    const response = await fetch('/api/analyze', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(text),
    });

    const data = await response.json();
    displayEnglishFrequency(data.englishFrequency);
    displayTextFrequency(data.textFrequency);
});

// Function to display the English letter frequency
function displayEnglishFrequency(englishFrequency) {
    const englishFrequencySection = document.getElementById('englishFrequencySection');
    const englishFrequencyTableBody = document.querySelector('#englishFrequencyTable tbody');

    // Clear existing table content
    englishFrequencyTableBody.innerHTML = '';

    // Populate English frequency table
    Object.keys(englishFrequency).forEach((letter) => {
        const row = `<tr>
                        <td>${letter}</td>
                        <td>${englishFrequency[letter]}%</td>
                    </tr>`;
        englishFrequencyTableBody.insertAdjacentHTML('beforeend', row);
    });

    englishFrequencySection.classList.remove('hidden');
}

// Function to display the frequency of letters in the text with input fields for replacement
function displayTextFrequency(textFrequency) {
    const textFrequencySection = document.getElementById('textFrequencySection');
    const textFrequencyTableBody = document.querySelector('#textFrequencyTable tbody');
    const totalLetters = Object.values(textFrequency).reduce((sum, freq) => sum + freq, 0);

    // Clear existing table content
    textFrequencyTableBody.innerHTML = '';

    // Populate text frequency table with replacement input
    Object.keys(textFrequency).forEach((letter) => {
        const percentage = ((textFrequency[letter] / totalLetters) * 100).toFixed(2);
        const row = `<tr>
                        <td>${letter}</td>
                        <td>${percentage}%</td>
                        <td><input type="text" class="replaceInput" maxlength="1" data-letter="${letter}" placeholder="New letter"></td>
                    </tr>`;
        textFrequencyTableBody.insertAdjacentHTML('beforeend', row);
    });

    textFrequencySection.classList.remove('hidden');
    document.getElementById('convertSection').classList.remove('hidden');
}

// Function to handle letter replacement
document.getElementById('convertButton').addEventListener('click', async () => {
    const text = document.getElementById('inputText').value;
    const replacements = {};

    // Gather all replacements from the input fields
    document.querySelectorAll('.replaceInput').forEach(input => {
        const oldChar = input.getAttribute('data-letter').toUpperCase();
        const newChar = input.value.toUpperCase();
        if (newChar) {
            replacements[oldChar] = newChar;
        }
    });

    const response = await fetch('/api/replace', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            text: text,
            replacements: replacements
        }),
    });

    const newText = await response.text();
    document.getElementById('outputText').textContent = newText;
});
