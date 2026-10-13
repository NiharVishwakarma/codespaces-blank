# Calendar Widget App

This project contains a simple dark-themed Android calendar widget with a red circle around the current date.

## Build with GitHub Actions (Option B)

This repository includes a GitHub Actions workflow that will build the APK automatically in the cloud.

### Steps

1. Push this project to a GitHub repository.
2. Go to the repository on GitHub.
3. Open the **Actions** tab.
4. Run the workflow manually or let it trigger on push.
5. Download the generated APK from the workflow artifact.

Workflow file:
- `.github/workflows/android-build.yml`

## Local build

```bash
./gradlew assembleDebug
```

The APK is generated in:

```text
app/build/outputs/apk/debug/
```

## Notes

- No Codemagic subscription is required.
- This uses the free GitHub Actions environment.
- No local Android SDK is needed when building in GitHub Actions.
