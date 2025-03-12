CREATE TABLE IF NOT EXISTS client (
                        chat_id BIGINT PRIMARY KEY,
                        registered_at TIMESTAMP(0),
                        current_command TEXT,
                        user_name TEXT,
                        first_name TEXT,
                        last_name TEXT,
                        source_language TEXT CHECK (source_language IN ('ARABIC','GERMAN','ENGLISH','SPANISH','FRENCH','HEBREW','ITALIAN','JAPANESE','KOREAN','DUTCH','POLISH','PORTUGUESE','ROMANIAN','RUSSIAN','SWEDISH','TURKISH','UKRAINIAN')),
                        target_language TEXT CHECK (target_language IN ('ARABIC','GERMAN','ENGLISH','SPANISH','FRENCH','HEBREW','ITALIAN','JAPANESE','KOREAN','DUTCH','POLISH','PORTUGUESE','ROMANIAN','RUSSIAN','SWEDISH','TURKISH','UKRAINIAN'))
);
