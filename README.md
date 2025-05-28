# Telegram Language Bot
<p align="center">
  <img src="logo.png" alt="Telegram Language Bot Logo" width="700" height="380">
</p>

**Telegram Language Bot** is a simple and functional assistant designed to help
you learn foreign languages or deepen your knowledge of your native one.
## Navigation
- [How to use the bot](#how-to-use)
    - [.env file](#env-file)
    -   [API_KEY](#api_key)
    -   [BOT_TOKEN](#bot_token)
    - [Running the App](#running-the-app)
-   [Available Commands](#available-Commands)

## How to use
The bot uses the [**Reverso Java API**](https://github.com/Anton3413/Reverso-Java-API)
to collect data for its responses.  
Under the hood, this API relies on [**ZenRows API**](https://www.zenrows.com/)
for web scraping.
## .env file
In the root directory of the project, there's a `.env` file 
where you need to insert your API keys for the project to run:

```dotenv
API_KEY= 
BOT_TOKEN=
```
### API_KEY

1. Go to [Zenrows](https://www.zenrows.com) and register for a free account (takes about a minute).
If you need help, follow this short [guide](https://github.com/Anton3413/Reverso-Java-API?tab=readme-ov-file#How-to-use)

2. Once you receive the key, add it to .env like so:

```dotenv
API_KEY=Y0u'v3g0tth1s!N0th1ng1s2h4rd1fUn3v3rg1v3up!
```

### BOT_TOKEN

1. Open Telegram and start a chat with [@BotFather](https://t.me/BotFather)

2. Send the command:

```
/newbot
```

3. Follow the instructions to choose a name and a unique username (ending in bot)
4. Copy the token BotFather provides and add it to .env like:

```dotenv
BOT_TOKEN=123456789:ABCdefGhIjkLmNoPQRstuvWxYz
```
### Running the App

Once both values are set in your .env, run the bot using Docker:
```shell
docker compose up
```

## Available Commands
- `/start` — The standard command used to start interacting with any Telegram bot.
  After that, you’ll be prompted to select a source and target language.
- `/language` — Set the source and target languages. You can change them anytime.
- `/translate` — Enables contextual translation mode (default). The bot will look 
- for how words are used in real context.
- `/synonyms` — Switches the bot mode. The bot will find synonyms for the word you 
- send in the source language.
- `/conjugation` — Switches the bot mode.Send the bot a verb in the source
- language, and it will return a full conjugation table.
- `/help` — Shows information about available commands. (Same as here 🙂)
- `/info` — Shows information about the creator.