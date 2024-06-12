B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=StaticCode
Version=12.8
@EndOfDesignText@
'Code module
'Subs in this code module will be accessible from all modules.
Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	Dim player As String = ""
	Dim Spanish As Boolean = False
	Dim Level2Pass As Boolean = False
	Dim Level3Pass As Boolean = False
	Dim Levels As String = ""
	Dim score As Long = 0

End Sub

Public Sub Func_SearchUser (user As String) As Boolean
	Dim ListUser As List
	Dim found As Boolean = False
	If File.Exists(File.DirInternal, "GameData.txt") Then
		ListUser = File.ReadList(File.DirInternal, "GameData.txt")
		For Each line In ListUser
			Dim value As String = line
			Dim arrayFind() As String = Regex.Split(",",value)
			If arrayFind(0) = user Then
				found = True
			End If
		Next
	End If
	Return found
End Sub

Public Sub Func_ConfirmUser (user As String, pass As String) As Boolean
	Dim ListUser As List
	Dim found As Boolean = False
	If File.Exists(File.DirInternal, "GameData.txt") Then
		ListUser = File.ReadList(File.DirInternal, "GameData.txt")
		For Each line In ListUser
			Dim value As String = line
			Dim arrayFind() As String = Regex.Split(",",value)
			If arrayFind(0) = user And arrayFind(1) = pass Then
				found = True
			End If
		Next
	End If
	Return found
End Sub

Public Sub Func_SearchScore (user As String) As Int
	Dim ListUser As List
	Dim scores As Long
	If File.Exists(File.DirInternal, "GameData.txt") Then
		ListUser = File.ReadList(File.DirInternal, "GameData.txt")
		For Each line In ListUser
			Dim value As String = line
			Dim arrayFind() As String = Regex.Split(",",value)
			If arrayFind(0) = user Then
				scores = arrayFind(4)
			End If
		Next
	End If
	Return scores
End Sub