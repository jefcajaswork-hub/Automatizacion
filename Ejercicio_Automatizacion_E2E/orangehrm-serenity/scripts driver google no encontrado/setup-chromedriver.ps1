param(
  [string]$InstallRoot = "$env:USERPROFILE\\chromedriver",
  [switch]$SetEnv,
  [switch]$Headless
)

Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'

function Get-ChromePath {
  $candidates = @(
    "$env:ProgramFiles\\Google\\Chrome\\Application\\chrome.exe",
    "$env:ProgramFiles(x86)\\Google\\Chrome\\Application\\chrome.exe",
    "$env:LOCALAPPDATA\\Google\\Chrome\\Application\\chrome.exe"
  )
  foreach ($p in $candidates) { if (Test-Path $p) { return $p } }
  throw 'Chrome no encontrado en rutas estándar.'
}

function Get-ChromeVersion {
  $chrome = Get-ChromePath
  return (Get-Item $chrome).VersionInfo.ProductVersion
}

function Get-DriverInfo($major) {
  # Intento 1: catálogo por milestone
  $urlMilestones = 'https://googlechromelabs.github.io/chrome-for-testing/latest-versions-per-milestone-with-downloads.json'
  try {
    $resp = Invoke-RestMethod -Uri $urlMilestones -UseBasicParsing
  } catch {
    $resp = $null
  }
  if ($resp -and $resp.milestones -and $resp.milestones.PSObject.Properties.Name -contains "$major") {
    $entry = $resp.milestones."$major"
    $drv = $entry.downloads.chromedriver | Where-Object { $_.platform -eq 'win64' } | Select-Object -First 1
    if ($drv) { return [pscustomobject]@{ Version = $entry.version; Url = $drv.url } }
  }

  # Intento 2: catálogo de versiones conocidas (fallback)
  $urlKnownGood = 'https://googlechromelabs.github.io/chrome-for-testing/known-good-versions-with-downloads.json'
  try {
    $kg = Invoke-RestMethod -Uri $urlKnownGood -UseBasicParsing
  } catch {
    throw "No se pudo obtener catálogos de Chrome for Testing: $($_.Exception.Message)"
  }
  $match = $kg.versions | Where-Object { $_.version -like "$major.*" } | Select-Object -Last 1
  if (-not $match) { throw "No se encontró versión para milestone $major" }
  $drv2 = $match.downloads.chromedriver | Where-Object { $_.platform -eq 'win64' } | Select-Object -First 1
  if (-not $drv2) { throw 'No se encontró paquete chromedriver win64 (fallback)' }
  return [pscustomobject]@{ Version = $match.version; Url = $drv2.url }
}

Write-Host 'Detectando versión de Chrome...'
$chromeVersion = Get-ChromeVersion
$major = $chromeVersion.Split('.')[0]
Write-Host "Chrome: $chromeVersion (major $major)"

Write-Host 'Buscando ChromeDriver correspondiente...'
$info = Get-DriverInfo -major $major
Write-Host "ChromeDriver: $($info.Version)"

$dest = Join-Path $InstallRoot $info.Version
New-Item -ItemType Directory -Force -Path $dest | Out-Null
$zip = Join-Path $dest 'chromedriver-win64.zip'

Write-Host "Descargando: $($info.Url)"
Invoke-WebRequest -Uri $info.Url -OutFile $zip

Write-Host 'Extrayendo...'
Expand-Archive -Path $zip -DestinationPath $dest -Force
$driverPath = Join-Path $dest 'chromedriver-win64\chromedriver.exe'
if (-not (Test-Path $driverPath)) { throw 'No se encontró chromedriver.exe tras la extracción' }

Write-Host "Driver en: $driverPath"

if ($SetEnv) {
  [Environment]::SetEnvironmentVariable('CHROMEDRIVER_PATH', $driverPath, 'User')
  Write-Host 'Variable de usuario CHROMEDRIVER_PATH configurada.'
  Write-Host 'Cierra y reabre tu terminal para que surta efecto.'
}

Write-Host ''
Write-Host 'Para ejecutar con Maven usando este driver:'
${switches} = "--remote-allow-origins=*,--disable-dev-shm-usage"
if ($Headless.IsPresent) {
  ${switches} = ${switches} + ",--headless=new"
}
$cmd = "mvn clean verify -Dwebdriver.driver=chrome -Dwebdriver.chrome.driver=`"$driverPath`" -Dchrome.switches=`"${switches}`""
Write-Host $cmd
