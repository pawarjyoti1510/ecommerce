# Posts products for the 8 new categories. categoryId is looked up by category code.
$base = "http://localhost:8088/api/inventory"
$idByCode = @{}
$categories = Invoke-RestMethod -Uri "$base/categories" -Method Get
foreach ($c in $categories) { $idByCode[$c.code.ToUpper()] = $c.id }
Write-Host "Categories found: $($idByCode.Keys -join ', ')"

$products = Get-Content -Raw -Encoding UTF8 "$PSScriptRoot\products-new-categories.json" | ConvertFrom-Json
foreach ($p in $products) {
    $categoryId = $idByCode[$p.categoryCode]
    if (-not $categoryId) {
        Write-Host "SKIP $($p.name): category $($p.categoryCode) not found in DB" -ForegroundColor Yellow
        continue
    }
    $p | Add-Member -NotePropertyName categoryId -NotePropertyValue $categoryId
    $p.PSObject.Properties.Remove('categoryCode')
    $body = $p | ConvertTo-Json -Compress
    try {
        $r = Invoke-RestMethod -Uri "$base/products" -Method Post `
             -ContentType "application/json; charset=utf-8" -Body ([Text.Encoding]::UTF8.GetBytes($body))
        Write-Host "OK   $($r.sku)  $($r.name)"
    } catch {
        Write-Host "FAIL $($p.name): $(if ($_.ErrorDetails.Message) { $_.ErrorDetails.Message } else { $_.Exception.Message })" -ForegroundColor Red
    }
}
