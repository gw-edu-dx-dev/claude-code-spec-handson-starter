# ハンズオン開始前の準備

## 1. 初期状態を確認
```bash
mvn test
mvn spring-boot:run
```

`http://localhost:8080/inquiries` を開き、以下を確認します。
- 問い合わせを登録できる
- キーワード検索できる
- ステータスで絞り込みできる
- 優先度はまだ存在しない

## 2. Gitの基準点を作る
差分レビューを行うため、ハンズオン開始時点をコミットしてください。

```bash
git init
git add .
git commit -m "Initial hands-on project"
```

すでにGitリポジトリとして配布している場合は、この操作は不要です。

## 3. Claude Codeを起動
```bash
claude
```

起動後、`HANDSON.md` の Step 1 から進めます。
