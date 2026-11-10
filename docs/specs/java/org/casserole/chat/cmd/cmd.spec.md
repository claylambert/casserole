# org.casserole.chat.cmd

Component file. It carries how this package is built.

**Depends:** [java/org/casserole/chat/chat.spec.md](../chat.spec.md), [java/org/casserole/config/config.spec.md](../../config/config.spec.md), [java/org/casserole/format/format.spec.md](../../format/format.spec.md), [java/org/casserole/ollama/ollama.spec.md](../../ollama/ollama.spec.md), [java/org/casserole/ssh/shell/shell.spec.md](../../ssh/shell/shell.spec.md), [java/org/roux/model/model.spec.md](../../../roux/model/model.spec.md)

# Casserole server

This page builds the Java host. The map is [program-spine.md](../../../aetheris/config/config.spec.md).


**semantic-key:** PK-0483 PK-0484 PK-0485 PK-0486 PK-0487

# A turn echoes white, spins, writes a yellow reply, and Ctrl+C adds no messages.

1. Type, with white echo, then Enter.
2. The spinner is on the current line.
3. The reply is yellow, or a table, with carriage-return line feed. The prompt returns.
4. `status` shows the user and assistant counts increased.
5. Ctrl+C during the wait writes a red `^C` and adds no new messages.

**Truth.** White type, spinner, yellow or table, counts move, Ctrl+C adds nothing.

**Reason.** The turn is the path the formatting and the cancel rules exist to protect.

**Avoidances.** Do not append on Ctrl+C. Do not color the typed echo yellow.


**semantic-key:** PK-0533

# A stopped engine prints a red error with the message and the first stack frame, and the listener stays up.

The engine is stopped. Chat Enter prints a red error containing the message and the first stack frame. The listener stays up.

The error color is `ESC[31m`. The error writer adds a carriage-return line feed.

**Truth.** Red error, first frame, listener still up.

**Reason.** The listener is independent of the model process.

**Avoidances.** Do not tear down the listener because the engine is down. Do not hide the first frame.


**semantic-key:** PK-0534

# The server file binds the shell and chat keys, and the demo context length 8192 is sent as num_ctx.

Bound to `CasseroleProperties` with prefix `casserole`, plus the model-library engine properties.

Shell block: port `2222`, a configured username, a configured password in plain text, and host key `hostkey.ser`. The username and password values are not published.

Chat block: `system-prompt` empty, `max-messages` 40, `escape-timeout-ms` 100, `max-paste-chars` 65536, `spinner` `ascii` with `braille` and `none` as the other names, `spinner-ms` 100, `smart-format` true, `table-style` `ascii` with `box` as the other name, `history-size` 500.

The unused datasource block: url `jdbc:h2:mem:casserole`, user name `sa`, password field empty, driver `org.h2.Driver`. JPA `ddl-auto` is `update`. This block is unused.

Engine block: `base-url` `http://localhost:11434`. `init.pull-model-strategy` `never`. Chat model `qwen2.5-coder:7b`. The same model is repeated under options. `options.num-ctx` is `8192`, the demo value sent as `num_ctx`. A context length of 0 omits the field and does not send `num_ctx`. The non-zero demo still sends 8192.

The child sections are the three key tables. No API key is present.

**Truth.** Port 2222, max messages 40, escape 100, paste cap 65536, spinner ascii at 100 ms, history 500, model `qwen2.5-coder:7b`, `num-ctx` 8192.

**Reason.** The file is the baseline the constraints promised to keep.

**Avoidances.** Do not publish the password. Do not send num_ctx when the value is 0. Do not treat the datasource as used.


**semantic-key:** PK-0536

# The prompt keeps the system message, takes the tail of non-system turns past the cap, and appends only after success.

On connect, a new `ChatSession` is created with:

1. The system message equal to `casserole.chat.system-prompt`.
2. `active` true, so the shell starts in `CHAT` and shows `chat>`.

Each turn:

1. Collect the system message.
2. Collect all non-system messages. If there are more than `max-messages`, take the tail.
3. Append the new user text, which is not yet stored.
4. Call the model.
5. On success only, append the user text and then the assistant text. A cancelled or failed turn does not grow history.

`message list` shows the full in-memory list, including the system message. The model only sees the capped prompt.

There is no token counting. The cap is message count, not tokens. That is a known gap for the vision that the product owns context-window management. The default cap is 40.

**Truth.** System plus the tail of non-system turns, then the new line. Persist only on success. List shows everything. Cap is 40 messages.

**Reason.** A failure must not become part of the next prompt.

**Avoidances.** Do not append on cancel or failure. Do not hide system from message list. Do not pretend the cap is tokens.


**semantic-key:** PK-0538

# The shell owns the reader, the three prompts, and the timers, and Enter in command mode hands the line to the processor.

`ChatShell` implements the listener command, `Runnable`, and the command context.

It owns:

- A UTF-8 reader on the channel.
- The line buffer, the Escape state machine, paste state, and the history cursor.
- Echo. Green in command mode. Default otherwise.
- Prompts `chat> `, `cmd> `, and `idle> `, each with a trailing space.
- The spinner timer, the escape timer, and the model executor.

It does not know command names. Enter in command mode hands the trimmed line to `CommandProcessor`.

The character pipeline, Escape, paste, history, the spinner, Ctrl+C, colors, width, and threads are the child sections.

**Truth.** Three prompts with trailing spaces. Green command echo. No command names inside the shell.

**Reason.** The shell is the line editor. The processor is the catalog.

**Avoidances.** Do not teach the shell the command catalog. Do not drop the trailing space on the prompts.


**semantic-key:** PK-0543 PK-0544

# The listener, the per-connection factory, the line editor, and the command processor are the server design.

The production listener is a thread started after the host is up. Each connection builds a session, a command set, a format router, and a shell. The shell owns the line editor and does not know command names. The processor matches the longest command. The child sections hold each of those rules.

Port 2222. Prompts `chat> `, `cmd> `, and `idle> `.

**Truth.** Listener, factory, editor, and commands. The shell does not know command names.

**Reason.** Command names live in the factory so the editor stays a line editor.

**Avoidances.** Do not start the listener in the constructor. Do not put command names inside the editor.


**semantic-key:** PK-0545 PK-0546

# Message list truncates at 120 characters unless the count is 0 or the command says verbose.

Approved.

| Item | Lock |
|---|---|
| Configuration | `casserole.chat.list-preview-chars: 120`. A value of `0` means never truncate. |
| Default | `message list`, `message list` with a filter, and `message list meta` are terse. Truncate and append `…`. |
| Full text | `message list verbose`, and `verbose user`, and `meta verbose`. |
| Parse | A flag on the existing command, the same style as `meta`. |
| Truncate | A count of Java `char` values. The text is already unmarshalled. |

**Truth.** Default 120. Zero means never truncate. Meta is terse too. Verbose is the full text.

**Reason.** A full card on every list row buried the sequence the operator was scanning.

**Avoidances.** Do not leave meta verbose by accident. Do not truncate verbose. Do not use a byte count.


**semantic-key:** PK-0552

## Attention geometry

The head and the tail are the spans a model attends, and the middle is the span it attends least.

The strongest attention sits on the head and the tail of the context window. The middle receives the weakest attention. The store uses that geometry: stable turns at the head, live turns at the tail, omitted turns in the middle.

**Truth.** Selection follows attention: head and tail ride, and the middle is the span that can stay off the wire.

**Reason.** A model already weights the ends. Putting the standing turns and the live turns there spends the window where attention is strongest.

**Avoidances.** Do not treat the middle as equal to the head or the tail. Do not drop the tail before the middle.


**semantic-key:** PK-0817 PK-0818 PK-0819 PK-0820 PK-0821 PK-0822 PK-0823 PK-0824 PK-0825 PK-0826 PK-0827 PK-0828 PK-0829 PK-0830 PK-0831 PK-0832 PK-0833 PK-0834 PK-0835 PK-0836 PK-0837 PK-0838 PK-0839 PK-0840 PK-0841 PK-0842 PK-0843 PK-0844 PK-0845 PK-0846 PK-0847 PK-0848 PK-0849 PK-0850 PK-0851 PK-0852 PK-0853 PK-0854 PK-0855 PK-0856 PK-0857 PK-0858 PK-0859 PK-0860 PK-0861 PK-0862 PK-0863

## Canvas door

On a canvas push, a turn the sink leaves off the wire stays in the vault and is left out of the push.

The conversation lives in the vault. The omitted turn is not written onto the push. It is not spilled into a second pack.

**Truth.** A canvas turn omitted from the wire stays in the vault and is absent from the push.

**Reason.** The vault is the record. The push is a projection. Dropping the vault turn because it missed the wire would make the projection the archive.

**Avoidances.** Do not delete the vault turn. Do not write the omitted turn into the push region. Do not create a second spill store for omitted canvas turns.


**semantic-key:** PK-0864 PK-0865 PK-0866 PK-0867 PK-0868 PK-0869 PK-0870 PK-0871 PK-0872 PK-0873 PK-0874 PK-0875 PK-0876 PK-0877 PK-0878 PK-0879 PK-0880 PK-0881 PK-0882 PK-0883 PK-0884 PK-0885 PK-0886 PK-0887 PK-0888 PK-0889 PK-0890 PK-0891 PK-0892 PK-0893 PK-0894 PK-0895 PK-0896 PK-0897 PK-0898 PK-0899 PK-0900 PK-0901 PK-0902 PK-0903 PK-0904 PK-0905 PK-0906 PK-0907 PK-0908 PK-0909 PK-0910

## Canvas gesture

The canvas lock sits beside the letter on a packed prefix of eligible cards from the top of the deck. Zero locks means that push has no canvas head.

The letter stays Included, Excluded, Input only, or Output only. The lock is the designation, not a letter. The deck grows at the bottom, so the top is the start of the deck. The walk starts there and skips Excluded. Included, Input only, and Output only are eligible. `head-count` is how many of those cards may lock, and the lock shows on those cards only. Presses keep a packed prefix from the first eligible card downward, so a lock cannot leave a hole. A locked card spends one of the count. The halves that card's letter sends ride with the head. A shorter prefix than the count is still a head. Zero locks means that push has no canvas head. Excluding a locked card drops that lock. The client reads `head-count` on connect. The terminal uses the same packed prefix, through `message lock` and `message unlock`. The control is painted when the stamped plan builds it. There is one source of truth for the count, and it is the server fact.

**Truth.** The canvas lock is a packed prefix of non-excluded cards from the top of the deck, capped by `head-count`. Zero locks means no canvas head. Excluding a locked card drops the lock.

**Reason.** The foremost cells that are not excluded are the ones the operator can pin as the frame. A hole would send a middle card as if it were head. One server count stops the client from inventing a second cap.

**Avoidances.** Do not lock an excluded card. Do not leave a hole in the prefix. Do not show the lock past `head-count`. Do not keep a lock on a card that becomes excluded. Do not invent a client-local count. Do not paint the control before the plan is stamped.


**semantic-key:** PK-0911 PK-0912 PK-0913 PK-0914 PK-0915 PK-0916 PK-0917 PK-0918 PK-0919 PK-0920 PK-0921 PK-0922 PK-0923 PK-0924 PK-0925 PK-0926 PK-0927 PK-0928 PK-0929 PK-0930 PK-0931 PK-0932 PK-0933 PK-0934 PK-0935 PK-0936 PK-0937 PK-0938 PK-0939 PK-0940 PK-0941 PK-0942 PK-0943 PK-0944 PK-0945 PK-0946 PK-0947 PK-0948 PK-0949 PK-0950 PK-0951 PK-0952 PK-0953 PK-0954 PK-0955 PK-0956 PK-0957

## Canvas lock

The canvas is where the operator designates the locked turns for that vault, and the vault still shows every turn.

The designation is a canvas control. The vault display stays the whole history.

**Truth.** Locked turns are designated on the canvas. The vault keeps showing every turn.

**Reason.** The wire is a selection. The vault is the tape. Hiding a non-locked turn would make the canvas the inspector.

**Avoidances.** Do not hide vault turns that are off the wire. Do not designate locked turns only on the terminal when the vault is the canvas record.


**semantic-key:** PK-0958 PK-0959 PK-0960 PK-0961 PK-0962 PK-0963 PK-0964 PK-0965 PK-0966 PK-0967 PK-0968 PK-0969 PK-0970 PK-0971 PK-0972 PK-0973 PK-0974 PK-0975 PK-0976 PK-0977 PK-0978 PK-0979 PK-0980 PK-0981 PK-0982 PK-0983 PK-0984 PK-0985 PK-0986 PK-0987 PK-0988 PK-0989 PK-0990 PK-0991 PK-0992 PK-0993 PK-0994 PK-0995 PK-0996 PK-0997 PK-0998 PK-0999 PK-1000 PK-1001 PK-1002 PK-1003 PK-1004

## Canvas push

The sink trims the canvas list before `holdRoux` adds it, and the provider receives that trimmed list.

When the transcript is pushed for the provider, the sink removes the earlier messages that will not fit, for the canvas only, because the canvas owns the push. `holdRoux` adds the trimmed list.

**Truth.** The canvas trim happens before `holdRoux`, and the provider receives that trimmed list.

**Reason.** `holdRoux` stores what it is given. Trimming after the add would write turns the provider must not see into the push region.

**Avoidances.** Do not trim after `holdRoux` has added the list. Do not apply this push trim to the terminal region.


**semantic-key:** PK-1005 PK-1006 PK-1007 PK-1008 PK-1009 PK-1010 PK-1011 PK-1012 PK-1013 PK-1014 PK-1015 PK-1016 PK-1017 PK-1018 PK-1019 PK-1020 PK-1021 PK-1022 PK-1023 PK-1024 PK-1025 PK-1026 PK-1027 PK-1028 PK-1029 PK-1030 PK-1031 PK-1032 PK-1033 PK-1034 PK-1035 PK-1036 PK-1037 PK-1038 PK-1039 PK-1040 PK-1041 PK-1042 PK-1043 PK-1044 PK-1045 PK-1046 PK-1047 PK-1048 PK-1049 PK-1050 PK-1051

## Canvas record

The canvas designation is stored with the vault. It is not stored on `user.aetheris`, because that region is replaced on each push.

`holdRoux` clears `user.aetheris` and adds the prompt. The vault turns are the cell records. Those sequences are not the sequences of that region. The vault stores the lock on the cell. On execute the client writes `head: true` for each locked cell that is already in the working set. The host does not read the vault, and it does not write the lock onto `user.aetheris`.

**Truth.** The canvas lock lives on the vault cell. Execute sends `head: true` only for a locked cell already in the working set. `user.aetheris` does not store the lock.

**Reason.** The push region is wiped on every push, so a lock written there would die before the next send. The vault cell survives. The host never sees the vault.

**Avoidances.** Do not persist the canvas lock on `user.aetheris`. Do not write `head: true` for a cell that is not in the working set. Do not make the host read the vault. Do not treat vault sequences as the push region's sequences.


**semantic-key:** PK-1052 PK-1053 PK-1054 PK-1055 PK-1056 PK-1057 PK-1058 PK-1059 PK-1060 PK-1061 PK-1062 PK-1063 PK-1064 PK-1065 PK-1066 PK-1067 PK-1068 PK-1069 PK-1070 PK-1071 PK-1072 PK-1073 PK-1074 PK-1075 PK-1076 PK-1077 PK-1078 PK-1079 PK-1080 PK-1081 PK-1082 PK-1083 PK-1084 PK-1085 PK-1086 PK-1087 PK-1088 PK-1089 PK-1090 PK-1091 PK-1092 PK-1093 PK-1094 PK-1095 PK-1096 PK-1097 PK-1098

## Channel cap

The sink omits middle turns so the transcript meets the managed size, and the send still goes. `AmwpCodec.fits` still refuses a frame that is too large for the channel. The sink does not drop the mood triple to pass that byte cap.

An execute that does not fit sets the cell to `too-large` and returns. A long canvas keeps the mood triple. The other client's fit path returns no frame and does not drop the mood seat. The order is assemble, then the sink when it is selecting, then encode, then `AmwpCodec.fits`. A miss sets `too-large` and returns. The mood triple is seated at assemble and stays through that miss.

**Truth.** Order is assemble, then the sink when selecting, then encode, then `AmwpCodec.fits`. A miss sets `too-large` and keeps the mood triple.

**Reason.** The managed size is tokens. The channel cap is bytes. Dropping the mood triple to pass the byte cap would change the prompt the operator seated. Trimming after encode would measure tokens on a different list than the one encoded.

**Avoidances.** Do not drop the mood triple to satisfy `AmwpCodec.fits`. Do not encode before the sink when the sink is selecting. Do not send a frame that fails `AmwpCodec.fits`. Do not clear `too-large` by removing the mood.


**semantic-key:** PK-1099 PK-1100 PK-1101 PK-1102 PK-1103 PK-1104 PK-1105 PK-1106 PK-1107 PK-1108 PK-1109 PK-1110 PK-1111 PK-1112 PK-1113 PK-1114 PK-1115 PK-1116 PK-1117 PK-1118 PK-1119 PK-1120 PK-1121 PK-1122 PK-1123 PK-1124 PK-1125 PK-1126 PK-1127 PK-1128 PK-1129 PK-1130 PK-1131 PK-1132 PK-1133 PK-1134 PK-1135 PK-1136 PK-1137 PK-1138 PK-1139 PK-1140 PK-1141 PK-1142 PK-1143 PK-1144 PK-1145

## Client clip

The client does not clip the transcript. The sink does.

Clipping stays on the host so both doors share one cut.

**Truth.** The client sends its working set unclipped. The sink clips.

**Reason.** Two clients clipping on their own will disagree about which tail atom still fits.

**Avoidances.** Do not clip in the client. Do not ask the client to mirror the sink's cut before the send.


**semantic-key:** PK-1146 PK-1147 PK-1148 PK-1149 PK-1150 PK-1151 PK-1152 PK-1153 PK-1154 PK-1155 PK-1156 PK-1157 PK-1158 PK-1159 PK-1160 PK-1161 PK-1162 PK-1163 PK-1164 PK-1165 PK-1166 PK-1167 PK-1168 PK-1169 PK-1170 PK-1171 PK-1172 PK-1173 PK-1174 PK-1175 PK-1176 PK-1177 PK-1178 PK-1179 PK-1180 PK-1181 PK-1182 PK-1183 PK-1184 PK-1185 PK-1186 PK-1187 PK-1188 PK-1189 PK-1190 PK-1191 PK-1192

## Clip range

This serial takes the clip-range seat on acceptance, and until that acceptance the clip-range page stays the filed proposal.

The clip-range page is the filed proposal on that seat. Acceptance of this serial is what retires the floor. Until then, the proposal page remains the filed proposal.

**Truth.** Clip range remains the filed proposal until this serial is accepted. Acceptance is what takes that seat.

**Reason.** Two live cursors, a floor sequence and a designated head, would both try to decide the window. The seat holds one of them.

**Avoidances.** Do not delete the clip-range page on acceptance. Do not run the floor and the designated head together. Do not treat this heading as a build of the clip-range file.


**semantic-key:** PK-1193 PK-1194 PK-1195 PK-1196 PK-1197 PK-1198 PK-1199 PK-1200 PK-1201 PK-1202 PK-1203 PK-1204 PK-1205 PK-1206 PK-1207 PK-1208 PK-1209 PK-1210 PK-1211 PK-1212 PK-1213 PK-1214 PK-1215 PK-1216 PK-1217 PK-1218 PK-1219 PK-1220 PK-1221 PK-1222 PK-1223 PK-1224 PK-1225 PK-1226 PK-1227 PK-1228 PK-1229 PK-1230 PK-1231 PK-1232 PK-1233 PK-1234 PK-1235 PK-1236 PK-1237 PK-1238 PK-1239

## Code seat

The type is the package-private class `ReichenbachSink` in `org.roux`, and `RouxEngine.windowIds` asks it for the ids when a head is designated.

The class sits where it fits the existing walk. It is not a public type. `windowIds` remains the caller when a head is designated.

**Truth.** `ReichenbachSink` is package-private in `org.roux`. `windowIds` asks it for ids only when a head is designated.

**Reason.** A public selector would be a second door beside the engine. Calling it with no head designated would replace today's walk.

**Avoidances.** Do not make `ReichenbachSink` public. Do not call it from `windowIds` when no head is designated. Do not give the other client the type.


**semantic-key:** PK-1240 PK-1241 PK-1242 PK-1243 PK-1244 PK-1245 PK-1246 PK-1247 PK-1248 PK-1249 PK-1250 PK-1251 PK-1252 PK-1253 PK-1254 PK-1255 PK-1256 PK-1257 PK-1258 PK-1259 PK-1260 PK-1261 PK-1262 PK-1263 PK-1264 PK-1265 PK-1266 PK-1267 PK-1268 PK-1269 PK-1270 PK-1271 PK-1272 PK-1273 PK-1274 PK-1275 PK-1276 PK-1277 PK-1278 PK-1279 PK-1280 PK-1281 PK-1282 PK-1283 PK-1284 PK-1285 PK-1286

## Dead page

The edit does not punch a page. A later prune may punch a page that no live index row names. The sink does not call that prune.

The edit appends the frame and retargets slot `seq - 1` in place. It does not call the prune. A later prune may punch a page that no live index row names, including a page whose only frames were superseded. The select path never punches.

**Truth.** Edit retargets the slot and leaves prior bytes. Selection never punches. A later prune may punch a page no live row names.

**Reason.** Punching inside the edit races the new frame. Punching inside selection mixes pack hygiene into the wire. The prune is a separate plan.

**Avoidances.** Do not punch from `message edit`. Do not punch from the select path. Do not call the prune from the sink. Do not reuse the old offset.


**semantic-key:** PK-1287 PK-1288 PK-1289 PK-1290 PK-1291 PK-1292 PK-1293 PK-1294 PK-1295 PK-1296 PK-1297 PK-1298 PK-1299 PK-1300 PK-1301 PK-1302 PK-1303 PK-1304 PK-1305 PK-1306 PK-1307 PK-1308 PK-1309 PK-1310 PK-1311 PK-1312 PK-1313 PK-1314 PK-1315 PK-1316 PK-1317 PK-1318 PK-1319 PK-1320 PK-1321 PK-1322 PK-1323 PK-1324 PK-1325 PK-1326 PK-1327 PK-1328 PK-1329 PK-1330 PK-1331 PK-1332 PK-1333

## Durable head

The head count and the designated sequences persist in a side record on the region, and the manifest stays 112 bytes.

The size of the protected head is set at runtime and persists across session recovery. The side record holds the count and the sequences. It is not stored by growing `manifest.bin`.

**Truth.** Head count and designated sequences persist beside the pack. `manifest.bin` stays 112 bytes.

**Reason.** Putting the head inside the manifest would change a file whose size readers already lock. A side record can appear and disappear without shifting page slots.

**Avoidances.** Do not grow `manifest.bin` to store the head. Do not drop the head count on recovery. Do not store the designated sequences only in memory for a disk region.


**semantic-key:** PK-1334 PK-1335 PK-1336 PK-1337 PK-1338 PK-1339 PK-1340 PK-1341 PK-1342 PK-1343 PK-1344 PK-1345 PK-1346 PK-1347 PK-1348 PK-1349 PK-1350 PK-1351 PK-1352 PK-1353 PK-1354 PK-1355 PK-1356 PK-1357 PK-1358 PK-1359 PK-1360 PK-1361 PK-1362 PK-1363 PK-1364 PK-1365 PK-1366 PK-1367 PK-1368 PK-1369 PK-1370 PK-1371 PK-1372 PK-1373 PK-1374 PK-1375 PK-1376 PK-1377 PK-1378 PK-1379 PK-1380

## Edit command

The console verb is `message edit` followed by the sequence.

The operator changes one stored turn by that sequence. The verb is not `message show`, and it is not a filtered row number.

**Truth.** The edit verb is `message edit <seq>`.

**Reason.** `message show` already takes the sequence and must keep opening a turn. A second meaning on show would mix a read with a write.

**Avoidances.** Do not overload `message show` as the edit. Do not address the edit by a filtered list index. Do not address it by the identity pair.


**semantic-key:** PK-1381 PK-1382 PK-1383 PK-1384 PK-1385 PK-1386 PK-1387 PK-1388 PK-1389 PK-1390 PK-1391 PK-1392 PK-1393 PK-1394 PK-1395 PK-1396 PK-1397 PK-1398 PK-1399 PK-1400 PK-1401 PK-1402 PK-1403 PK-1404 PK-1405 PK-1406 PK-1407 PK-1408 PK-1409 PK-1410 PK-1411 PK-1412 PK-1413 PK-1414 PK-1415 PK-1416 PK-1417 PK-1418 PK-1419 PK-1420 PK-1421 PK-1422 PK-1423 PK-1424 PK-1425 PK-1426 PK-1427

## Edit end

A multi-line `message edit` ends on a line that is exactly `<.>`. That line is not body text. A line that is `.` is body text. The lines before the tag join with LF.

A lone dot is not clear enough on a text terminal, because the body may contain a dot line. The tag `<.>` is unambiguous.

**Truth.** The end tag is a line that is exactly `<.>`. It is not stored. A line that is `.` is stored. Joined lines use LF.

**Reason.** A bare dot collides with real message text. The tag has to be a line the body can still contain as something else, and `.` remains legal body.

**Avoidances.** Do not end the edit on a lone dot. Do not include the `<.>` line in the body. Do not join lines with a carriage return. Do not treat `<.>` in the middle of a longer line as the tag unless that line is exactly the tag.


**semantic-key:** PK-1428 PK-1429 PK-1430 PK-1431 PK-1432 PK-1433 PK-1434 PK-1435 PK-1436 PK-1437 PK-1438 PK-1439 PK-1440 PK-1441 PK-1442 PK-1443 PK-1444 PK-1445 PK-1446 PK-1447 PK-1448 PK-1449 PK-1450 PK-1451 PK-1452 PK-1453 PK-1454 PK-1455 PK-1456 PK-1457 PK-1458 PK-1459 PK-1460 PK-1461 PK-1462 PK-1463 PK-1464 PK-1465 PK-1466 PK-1467 PK-1468 PK-1469 PK-1470 PK-1471 PK-1472 PK-1473 PK-1474

## Edit frame

The revised text is a new frame, and the index row for that sequence follows the new frame.

The store stays append-only. Scrolling by turn id already exists. The edit does not rewrite the old bytes in place.

**Truth.** An edit appends a frame and points the existing sequence at it.

**Reason.** Rewriting the old offset would destroy the append-only pack and the crash story that the log is the truth.

**Avoidances.** Do not overwrite the old frame in place. Do not mint a new sequence for the revision. Do not leave the index pointing at the old offset.


**semantic-key:** PK-1475 PK-1476 PK-1477 PK-1478 PK-1479 PK-1480 PK-1481 PK-1482 PK-1483 PK-1484 PK-1485 PK-1486 PK-1487 PK-1488 PK-1489 PK-1490 PK-1491 PK-1492 PK-1493 PK-1494 PK-1495 PK-1496 PK-1497 PK-1498 PK-1499 PK-1500 PK-1501 PK-1502 PK-1503 PK-1504 PK-1505 PK-1506 PK-1507 PK-1508 PK-1509 PK-1510 PK-1511 PK-1512 PK-1513 PK-1514 PK-1515 PK-1516 PK-1517 PK-1518 PK-1519 PK-1520 PK-1521

## Edit of a head turn

The operator revises one designated head turn from the terminal, and the next transcript uses that text.

The operator lists the tape, opens the sequence, and then edits that sequence. A body on the same line is the new text. A bare sequence reads lines until a line that is exactly `<.>`. An empty body refuses. A sequence the index does not hold refuses. The pack stays append-only. `MonotonicIndex.append` still mints only `size + 1`. The edit does not call it, and it does not mint a Roux id. The new frame is written at the end of the hot page. The index slot for that sequence, slot `seq - 1`, moves to the new page, offset, length, and token count. The sequence stays. The Roux id stays. The role stays. `nextMessageId` stays. The page high-water id stays the newest sequence. The previous bytes stay at the old offset, and the current row no longer points at them. When the sequence is in the designated head, the next wire reads the new text. The edit itself does not send. The edit leaves the lock as it is. The same flow applies to a sequence that is on the tape and outside the head. The picture of this flow is illustrative only and is not a second rule.

**Truth.** `message edit` appends a frame at the hot page's end, retargets slot `seq - 1`, and leaves sequence, Roux id, role, lock, and `nextMessageId` unchanged. It does not send.

**Reason.** Minting a new sequence would split one turn into two rows. Minting a new Roux id would break the identity of the turn. Sending from the edit would turn a revision into a provider call. Clearing the lock would force the operator to lock again after every typo.

**Avoidances.** Do not call `MonotonicIndex.append` from the edit. Do not mint a Roux id. Do not change `nextMessageId`. Do not move the page high-water id to the edited sequence. Do not point the live row at the old offset. Do not send on edit. Do not clear or set the lock. Do not accept an empty body or an unknown sequence.


**semantic-key:** PK-1522 PK-1523 PK-1524 PK-1525 PK-1526 PK-1527 PK-1528 PK-1529 PK-1530 PK-1531 PK-1532 PK-1533 PK-1534 PK-1535 PK-1536 PK-1537 PK-1538 PK-1539 PK-1540 PK-1541 PK-1542 PK-1543 PK-1544 PK-1545 PK-1546 PK-1547 PK-1548 PK-1549 PK-1550 PK-1551 PK-1552 PK-1553 PK-1554 PK-1555 PK-1556 PK-1557 PK-1558 PK-1559 PK-1560 PK-1561 PK-1562 PK-1563 PK-1564 PK-1565 PK-1566 PK-1567 PK-1568 PK-1569

## Forbidden invention

A synthesis pass does not compact the pack, grow the manifest, add a public type, or place a middle sequence on the wire.

Compaction is eliminated. The manifest stays 112 bytes. The selector stays package-private. The middle stays off the wire.

**Truth.** Synthesis may not compact, grow the manifest, publish the type, or send a middle sequence.

**Reason.** Those four moves are the shapes this serial closed. A later pass that adds them rebuilds the failure.

**Avoidances.** Do not compact the pack. Do not grow `manifest.bin`. Do not add a public type. Do not place a middle sequence on the wire.


**semantic-key:** PK-1570 PK-1571 PK-1572 PK-1573 PK-1574 PK-1575 PK-1576 PK-1577 PK-1578 PK-1579 PK-1580 PK-1581 PK-1582 PK-1583 PK-1584 PK-1585 PK-1586 PK-1587 PK-1588 PK-1589 PK-1590 PK-1591 PK-1592 PK-1593 PK-1594 PK-1595 PK-1596 PK-1597 PK-1598 PK-1599 PK-1600 PK-1601 PK-1602 PK-1603 PK-1604 PK-1605 PK-1606 PK-1607 PK-1608 PK-1609 PK-1610 PK-1611 PK-1612 PK-1613 PK-1614 PK-1615 PK-1616

## Head count config

The locked head is a count of turns set at config time, and that count is not a token size.

The host reports it as the line `head-count` on the session facts beside `managed-tokens` and `num-ctx`. The client reads that line on connect. Absent or `0` means no count. That integer is the count both doors use. The operator inserts context into these turns. The number of turns is irrespective of their token width.

**Truth.** `head-count` is a turn count on the session facts beside `managed-tokens` and `num-ctx`. Absent or `0` means no count. Both doors use that integer.

**Reason.** One reported count keeps the terminal prefix and the canvas prefix on the same cap. A token width would move whenever the text moved, which is a different duty.

**Avoidances.** Do not report the head as a token size. Do not use a different count on each door. Do not treat a missing line or `0` as a default lock of the leading turns.


**semantic-key:** PK-1617 PK-1618 PK-1619 PK-1620 PK-1621 PK-1622 PK-1623 PK-1624 PK-1625 PK-1626 PK-1627 PK-1628 PK-1629 PK-1630 PK-1631 PK-1632 PK-1633 PK-1634 PK-1635 PK-1636 PK-1637 PK-1638 PK-1639 PK-1640 PK-1641 PK-1642 PK-1643 PK-1644 PK-1645 PK-1646 PK-1647 PK-1648 PK-1649 PK-1650 PK-1651 PK-1652 PK-1653 PK-1654 PK-1655 PK-1656 PK-1657 PK-1658 PK-1659 PK-1660 PK-1661 PK-1662 PK-1663

## Head default

The eligible run is the first `head-count` non-system turns, in sequence order. Zero locks means the terminal send has no head, and the walk stays the one it uses today. A shorter locked prefix than the count is still a head. The canvas head is the locked prefix of eligible cards.

Eligibility is who may lock. Designation is who is locked. An empty designation is not an implicit lock of the leading run.

**Truth.** Eligible turns are the first `head-count` non-system turns in order. Zero locks means no head. A shorter prefix than `head-count` is still a head.

**Reason.** The count is a ceiling, not a quota that must be filled. Treating a short prefix as "no head" would throw the operator back to today's walk in the middle of a lock.

**Avoidances.** Do not lock the leading run automatically when nothing is locked. Do not refuse a prefix shorter than `head-count`. Do not make system turns eligible for the head count.


**semantic-key:** PK-1664 PK-1665 PK-1666 PK-1667 PK-1668 PK-1669 PK-1670 PK-1671 PK-1672 PK-1673 PK-1674 PK-1675 PK-1676 PK-1677 PK-1678 PK-1679 PK-1680 PK-1681 PK-1682 PK-1683 PK-1684 PK-1685 PK-1686 PK-1687 PK-1688 PK-1689 PK-1690 PK-1691 PK-1692 PK-1693 PK-1694 PK-1695 PK-1696 PK-1697 PK-1698 PK-1699 PK-1700 PK-1701 PK-1702 PK-1703 PK-1704 PK-1705 PK-1706 PK-1707 PK-1708 PK-1709 PK-1710

## Head lock

Those designated turns stay on every wire until the operator changes them.

The lock holds across sends. It is not recomputed from recency.

**Truth.** A designated turn rides on every wire until the operator changes the designation.

**Reason.** A lock that expires when the tail grows is not a lock. The operator is the only writer of membership.

**Avoidances.** Do not drop a designated turn to make room for a newer tail turn. Do not clear the lock because a send succeeded.


**semantic-key:** PK-1711 PK-1712 PK-1713 PK-1714 PK-1715 PK-1716 PK-1717 PK-1718 PK-1719 PK-1720 PK-1721 PK-1722 PK-1723 PK-1724 PK-1725 PK-1726 PK-1727 PK-1728 PK-1729 PK-1730 PK-1731 PK-1732 PK-1733 PK-1734 PK-1735 PK-1736 PK-1737 PK-1738 PK-1739 PK-1740 PK-1741 PK-1742 PK-1743 PK-1744 PK-1745 PK-1746 PK-1747 PK-1748 PK-1749 PK-1750 PK-1751 PK-1752 PK-1753 PK-1754 PK-1755 PK-1756 PK-1757

## Head order

Designated head turns ride in ascending sequence order. The terminal designation is a packed prefix, so the list has no hole. A sequence that is not designated, and is older than the newest tail atom, is middle.

The designated sequences are sorted ascending and placed after the pin. The tail walk skips those sequences, walks what remains newest first, and places the atoms that fit after the head in chronological order. The terminal list is a packed prefix, so a hole is not in that list. The canvas prefix stays packed.

**Truth.** Head turns ride in ascending sequence order after the pin. Both doors use a packed prefix with no hole. Anything undesignated and older than the newest tail atom is middle.

**Reason.** Today's walk sends one contiguous newest span and does not name head order. An unsorted head would put a locked turn inside the tail. A hole would put a middle sequence on the wire.

**Avoidances.** Do not send the head in lock-time order if that differs from sequence order. Do not leave a hole in the prefix. Do not treat an undesignated older sequence as tail.


**semantic-key:** PK-1758 PK-1759 PK-1760 PK-1761 PK-1762 PK-1763 PK-1764 PK-1765 PK-1766 PK-1767 PK-1768 PK-1769 PK-1770 PK-1771 PK-1772 PK-1773 PK-1774 PK-1775 PK-1776 PK-1777 PK-1778 PK-1779 PK-1780 PK-1781 PK-1782 PK-1783 PK-1784 PK-1785 PK-1786 PK-1787 PK-1788 PK-1789 PK-1790 PK-1791 PK-1792 PK-1793 PK-1794 PK-1795 PK-1796 PK-1797 PK-1798 PK-1799 PK-1800 PK-1801 PK-1802 PK-1803 PK-1804

## Head over budget

When the designated head alone exceeds the managed window, the wire sends the head, the tail is empty, and the console says the head is over the allowance.

The sink does not clip the head to avoid a provider clip. It reports the overage and still sends the head. The historical tail stays empty.

**Truth.** An over-allowance head is sent whole, the tail is empty, and the console says the head is over the allowance.

**Reason.** Clipping the head would drop a locked turn. The operator's duty is the head width. The console sentence is how the operator learns the duty was missed.

**Avoidances.** Do not drop head turns to fit the allowance. Do not fill a historical tail beside an over-allowance head. Do not stay silent when the head exceeds the allowance.


**semantic-key:** PK-1805 PK-1806 PK-1807 PK-1808 PK-1809 PK-1810 PK-1811 PK-1812 PK-1813 PK-1814 PK-1815 PK-1816 PK-1817 PK-1818 PK-1819 PK-1820 PK-1821 PK-1822 PK-1823 PK-1824 PK-1825 PK-1826 PK-1827 PK-1828 PK-1829 PK-1830 PK-1831 PK-1832 PK-1833 PK-1834 PK-1835 PK-1836 PK-1837 PK-1838 PK-1839 PK-1840 PK-1841 PK-1842 PK-1843 PK-1844 PK-1845 PK-1846 PK-1847 PK-1848 PK-1849 PK-1850 PK-1851

## Head width

The locked head's token width is the content of those turns.

The head can be of variable token width. The width depends on the content it holds.

**Truth.** Head width is the token width of the designated turns' content, not a fixed budget of its own.

**Reason.** The operator chooses the turns. Their text decides the width. A fixed head width would clip the frame the lock exists to keep.

**Avoidances.** Do not assign the head a constant token width. Do not ignore content when measuring the head.


**semantic-key:** PK-1852 PK-1853 PK-1854 PK-1855 PK-1856 PK-1857 PK-1858 PK-1859 PK-1860 PK-1861 PK-1862 PK-1863 PK-1864 PK-1865 PK-1866 PK-1867 PK-1868 PK-1869 PK-1870 PK-1871 PK-1872 PK-1873 PK-1874 PK-1875 PK-1876 PK-1877 PK-1878 PK-1879 PK-1880 PK-1881 PK-1882 PK-1883 PK-1884 PK-1885 PK-1886 PK-1887 PK-1888 PK-1889 PK-1890 PK-1891 PK-1892 PK-1893 PK-1894 PK-1895 PK-1896 PK-1897 PK-1898

## Head width duty

The operator manages the token width of the locked head.

The size can be arbitrary. It is up to the operator to manage the token size of the locked turn region that forms the fixed head.

**Truth.** Head width is the operator's duty.

**Reason.** The sink will still send the head when it exceeds the allowance. Only the operator can shrink the text that caused that.

**Avoidances.** Do not auto-truncate the locked head to fit the allowance. Do not treat an arbitrary head width as a sink error that drops turns.


**semantic-key:** PK-1899 PK-1900 PK-1901 PK-1902 PK-1903 PK-1904 PK-1905 PK-1906 PK-1907 PK-1908 PK-1909 PK-1910 PK-1911 PK-1912 PK-1913 PK-1914 PK-1915 PK-1916 PK-1917 PK-1918 PK-1919 PK-1920 PK-1921 PK-1922 PK-1923 PK-1924 PK-1925 PK-1926 PK-1927 PK-1928 PK-1929 PK-1930 PK-1931 PK-1932 PK-1933 PK-1934 PK-1935 PK-1936 PK-1937 PK-1938 PK-1939 PK-1940 PK-1941 PK-1942 PK-1943 PK-1944 PK-1945

## Heatbar budget

The terminal heat bar and the canvas heat bar use the managed token size as the budget in the estimated-token calculation.

That size replaces the chat window size those bars use today. Both surfaces share it.

**Truth.** Both heat bars budget against the managed token size.

**Reason.** A bar that still uses the old chat window shows a different ceiling than the transcript the sink will send.

**Avoidances.** Do not leave either bar on the old chat-window size once the managed size is the budget. Do not give the two bars different managed budgets.


**semantic-key:** PK-1946 PK-1947 PK-1948 PK-1949 PK-1950 PK-1951 PK-1952 PK-1953 PK-1954 PK-1955 PK-1956 PK-1957 PK-1958 PK-1959 PK-1960 PK-1961 PK-1962 PK-1963 PK-1964 PK-1965 PK-1966 PK-1967 PK-1968 PK-1969 PK-1970 PK-1971 PK-1972 PK-1973 PK-1974 PK-1975 PK-1976 PK-1977 PK-1978 PK-1979 PK-1980 PK-1981 PK-1982 PK-1983 PK-1984 PK-1985 PK-1986 PK-1987 PK-1988 PK-1989 PK-1990 PK-1991 PK-1992

## Inspector

The inspector shows only the transcript the sink will send, and it reads that list on the client.

The inspector must show only what the wire allows and will transmit for the configured token size. Whether that list is computed with the server is an assessment. The decision on this page is that the client reads the wire transcript.

**Truth.** The inspector's turn list is the wire transcript, read on the client.

**Reason.** An inspector that shows the whole tape tells the operator the provider saw turns it did not see.

**Avoidances.** Do not paint omitted turns as inspector rows. Do not make the inspector a second selection with different rules.


**semantic-key:** PK-1993 PK-1994 PK-1995 PK-1996 PK-1997 PK-1998 PK-1999 PK-2000 PK-2001 PK-2002 PK-2003 PK-2004 PK-2005 PK-2006 PK-2007 PK-2008 PK-2009 PK-2010 PK-2011 PK-2012 PK-2013 PK-2014 PK-2015 PK-2016 PK-2017 PK-2018 PK-2019 PK-2020 PK-2021 PK-2022 PK-2023 PK-2024 PK-2025 PK-2026 PK-2027 PK-2028 PK-2029 PK-2030 PK-2031 PK-2032 PK-2033 PK-2034 PK-2035 PK-2036 PK-2037 PK-2038 PK-2039 PK-2040

## Laminar flow

The sink selects as part of the read and as part of the push. No compaction pass runs.

The selection is the flow. It is not a later rewrite of the store.

**Truth.** Selection runs on the terminal read and on the canvas push, and there is no compaction pass.

**Reason.** A separate compaction pass would rewrite bytes the selection only needed to skip.

**Avoidances.** Do not add a compaction pass beside the read or the push. Do not select by rewriting the tape.


**semantic-key:** PK-2041 PK-2042 PK-2043 PK-2044 PK-2045 PK-2046 PK-2047 PK-2048 PK-2049 PK-2050 PK-2051 PK-2052 PK-2053 PK-2054 PK-2055 PK-2056 PK-2057 PK-2058 PK-2059 PK-2060 PK-2061 PK-2062 PK-2063 PK-2064 PK-2065 PK-2066 PK-2067 PK-2068 PK-2069 PK-2070 PK-2071 PK-2072 PK-2073 PK-2074 PK-2075 PK-2076 PK-2077 PK-2078 PK-2079 PK-2080 PK-2081 PK-2082 PK-2083 PK-2084 PK-2085 PK-2086 PK-2087

## Live line

When the sink selects, the user line being sent is the newest tail atom. Its tokens come out of the tail budget, and it rides when it has no assistant reply. The unset walk still appends that line after the window and does not charge it against `max-tokens`.

`buildPrompt` appends that line after the window returns. The line is stored only after a non-empty reply. The sink charges the unsent user line before the historical tail is filled. The line rides when it has no reply, and it rides when it is larger than the remainder. The historical tail is then empty. The console reports the head only when the pin and the head themselves exceed the managed size. The unset path still appends the line after `windowIds` and does not charge it.

**Truth.** The selecting sink charges the unsent user line before the historical tail, and the line rides even when it overruns the remainder. The unset path appends it after the window and does not charge it.

**Reason.** The line being sent is the live edge. Filling history first would drop the turn the operator just typed. Charging it on the unset path would change today's window.

**Avoidances.** Do not fill the historical tail before charging the live line. Do not drop the live line when it is larger than the remainder. Do not report an over-allowance head unless the pin and the head themselves exceed the size. Do not charge the live line against `max-tokens` on the unset path.


**semantic-key:** PK-2088 PK-2089 PK-2090 PK-2091 PK-2092 PK-2093 PK-2094 PK-2095 PK-2096 PK-2097 PK-2098 PK-2099 PK-2100 PK-2101 PK-2102 PK-2103 PK-2104 PK-2105 PK-2106 PK-2107 PK-2108 PK-2109 PK-2110 PK-2111 PK-2112 PK-2113 PK-2114 PK-2115 PK-2116 PK-2117 PK-2118 PK-2119 PK-2120 PK-2121 PK-2122 PK-2123 PK-2124 PK-2125 PK-2126 PK-2127 PK-2128 PK-2129 PK-2130 PK-2131 PK-2132 PK-2133 PK-2134

## Managed size

The managed window is a token size set at config time, and that size is the threshold the sink keeps.

It is the general size boundary, in tokens, that the sink manages. It is not a count of turns.

**Truth.** The managed size is a configured token threshold, and the sink spends against that threshold.

**Reason.** Turns vary in width. A turn count cannot say whether the head and the tail still fit inside the provider allocation.

**Avoidances.** Do not treat the managed size as a turn count. Do not leave the threshold implicit.


**semantic-key:** PK-2135 PK-2136 PK-2137 PK-2138 PK-2139 PK-2140 PK-2141 PK-2142 PK-2143 PK-2144 PK-2145 PK-2146 PK-2147 PK-2148 PK-2149 PK-2150 PK-2151 PK-2152 PK-2153 PK-2154 PK-2155 PK-2156 PK-2157 PK-2158 PK-2159 PK-2160 PK-2161 PK-2162 PK-2163 PK-2164 PK-2165 PK-2166 PK-2167 PK-2168 PK-2169 PK-2170 PK-2171 PK-2172 PK-2173 PK-2174 PK-2175 PK-2176 PK-2177 PK-2178 PK-2179 PK-2180 PK-2181

## Managed window

The operator sets that transcript smaller than the provider context.

The allowed size of the middle and the tail is operator-tunable. It is a smaller managed region inside the larger actual window, so the session does not meet a context clip from the provider.

**Truth.** The managed window is an operator size strictly inside the provider context.

**Reason.** If the managed size is the provider size, the provider still clips. A smaller managed region keeps the clip on the host.

**Avoidances.** Do not set the managed window equal to the provider window and call the clip solved. Do not let the provider decide the cut.


**semantic-key:** PK-2182 PK-2183 PK-2184 PK-2185 PK-2186 PK-2187 PK-2188 PK-2189 PK-2190 PK-2191 PK-2192 PK-2193 PK-2194 PK-2195 PK-2196 PK-2197 PK-2198 PK-2199 PK-2200 PK-2201 PK-2202 PK-2203 PK-2204 PK-2205 PK-2206 PK-2207 PK-2208 PK-2209 PK-2210 PK-2211 PK-2212 PK-2213 PK-2214 PK-2215 PK-2216 PK-2217 PK-2218 PK-2219 PK-2220 PK-2221 PK-2222 PK-2223 PK-2224 PK-2225 PK-2226 PK-2227 PK-2228 PK-2229

## Message cap

When the sink is selecting, `max-messages` does not take a second bite out of the tail. The unset walk still honors `max-messages`, and a pair still rides when that count is 1.

The checked-in `chat.max-messages` is 200. The code default when the key is absent is 40. `windowIds` with `max-messages` 1 still returns both frames of the newest pair. `buildPrompt` passes `max-messages` only into today's `windowIds`. The sink's walk does not read that count. The pair that rides when the count is 1 stays inside that existing walk.

**Truth.** The selecting sink ignores `max-messages`. The unset walk still uses it, defaulting to 40 when the key is absent and to the checked-in 200 when that value is present, and a count of 1 still returns both frames of the newest pair.

**Reason.** The managed size is the tail budget. A second message cap would drop atoms that still fit. The pair-at-1 rule belongs to the walk the tree already has.

**Avoidances.** Do not apply `max-messages` inside the selecting sink. Do not pass that count anywhere but today's `windowIds`. Do not split the newest pair when `max-messages` is 1.


**semantic-key:** PK-2230 PK-2231 PK-2232 PK-2233 PK-2234 PK-2235 PK-2236 PK-2237 PK-2238 PK-2239 PK-2240 PK-2241 PK-2242 PK-2243 PK-2244 PK-2245 PK-2246 PK-2247 PK-2248 PK-2249 PK-2250 PK-2251 PK-2252 PK-2253 PK-2254 PK-2255 PK-2256 PK-2257 PK-2258 PK-2259 PK-2260 PK-2261 PK-2262 PK-2263 PK-2264 PK-2265 PK-2266 PK-2267 PK-2268 PK-2269 PK-2270 PK-2271 PK-2272 PK-2273 PK-2274 PK-2275 PK-2276 PK-2277

## Mood clear

A mood move does not clear the region, and it does not rewrite a designated head sequence. The clear-and-append path stays off a designated head.

`replaceTranscript` clears the region and appends the tape again. A mood move uses that path. `MonotonicIndex.append` mints `size + 1` and would assign new sequences. `replaceTranscript` returns before the clear when a head is designated. Mood still seats the prompt copy after the sink returns. The stored bytes of a designated sequence stay. When no head is designated, `replaceTranscript` stays the clear-and-append it is today.

**Truth.** A designated head makes `replaceTranscript` return before the clear. With no head, clear-and-append stays.

**Reason.** Clearing remints sequences, so the side record's sequence list would point at different turns. The prompt copy can still receive the mood after selection without rewriting the pack.

**Avoidances.** Do not clear the region on a mood move while a head is designated. Do not rewrite a designated sequence to seat mood. Do not change clear-and-append when no head is designated. Do not stop mood from seating the prompt copy.


**semantic-key:** PK-2278 PK-2279 PK-2280 PK-2281 PK-2282 PK-2283 PK-2284 PK-2285 PK-2286 PK-2287 PK-2288 PK-2289 PK-2290 PK-2291 PK-2292 PK-2293 PK-2294 PK-2295 PK-2296 PK-2297 PK-2298 PK-2299 PK-2300 PK-2301 PK-2302 PK-2303 PK-2304 PK-2305 PK-2306 PK-2307 PK-2308 PK-2309 PK-2310 PK-2311 PK-2312 PK-2313 PK-2314 PK-2315 PK-2316 PK-2317 PK-2318 PK-2319 PK-2320 PK-2321 PK-2322 PK-2323 PK-2324 PK-2325

## Mood pin word

The mood pin value `head` chooses which user turn wears the splice. It is not the locked head.

`moodPins` value `head` is that splice seat. The sink never reads `moodPins`. The value `head` stays the splice seat already applied to the prompt copy. Terminal sequences come from `head.yaml`. A canvas turn carries `head: true` when the operator locked that cell.

**Truth.** The word `head` on the mood pin is the splice seat. The locked head is `head.yaml` on the terminal and `head: true` on a canvas turn. The sink does not read `moodPins`.

**Reason.** One word was already used for the splice. Reusing it as the lock would make a mood setting designate turns, and the sink would depend on a pin table that is not the side record.

**Avoidances.** Do not treat `moodPins` value `head` as the locked head. Do not read `moodPins` from the sink. Do not store the terminal designation anywhere but `head.yaml`. Do not store the canvas designation anywhere but the cell's `head: true`.


**semantic-key:** PK-2326 PK-2327 PK-2328 PK-2329 PK-2330 PK-2331 PK-2332 PK-2333 PK-2334 PK-2335 PK-2336 PK-2337 PK-2338 PK-2339 PK-2340 PK-2341 PK-2342 PK-2343 PK-2344 PK-2345 PK-2346 PK-2347 PK-2348 PK-2349 PK-2350 PK-2351 PK-2352 PK-2353 PK-2354 PK-2355 PK-2356 PK-2357 PK-2358 PK-2359 PK-2360 PK-2361 PK-2362 PK-2363 PK-2364 PK-2365 PK-2366 PK-2367 PK-2368 PK-2369 PK-2370 PK-2371 PK-2372

## No summary

The sink does not replace an omitted turn with a summary. The turn stays itself.

No intent is lost to a compaction that becomes a summary.

**Truth.** An omitted turn is omitted whole. It is not summarized.

**Reason.** A summary is a different text. The tape the operator owns would no longer be the tape the store holds.

**Avoidances.** Do not summarize omitted turns. Do not compact the omitted span into a stand-in message.


**semantic-key:** PK-2373 PK-2374 PK-2375 PK-2376 PK-2377 PK-2378 PK-2379 PK-2380 PK-2381 PK-2382 PK-2383 PK-2384 PK-2385 PK-2386 PK-2387 PK-2388 PK-2389 PK-2390 PK-2391 PK-2392 PK-2393 PK-2394 PK-2395 PK-2396 PK-2397 PK-2398 PK-2399 PK-2400 PK-2401 PK-2402 PK-2403 PK-2404 PK-2405 PK-2406 PK-2407 PK-2408 PK-2409 PK-2410 PK-2411 PK-2412 PK-2413 PK-2414 PK-2415 PK-2416 PK-2417 PK-2418 PK-2419

## One counter

When the managed size is set, the heat bar counts the wire transcript with `HeuristicTokenCounter` and fills against that size. Stored terminal frames spend the token count on the index row. The unsent line uses the same counter. When the size is unset, the bar keeps today's count and today's window.

`windowIds` adds the index entry's token count. The canvas bar counts one render of the kept working set, not that sum. `HeuristicTokenCounter` counts `(length + 3) / 4`, and an empty body counts as 1. There are three bands. Unset keeps today's numerator and today's denominator. A set size with no head designated changes only the denominator to `managed-tokens`, and the numerator stays today's count. When the sink is selecting, the numerator is the sink's sum: the index token count for a stored frame, and `HeuristicTokenCounter` for the unsent line and for a canvas body that is not in the index yet.

**Truth.** Three bands share one counter. Unset keeps both today's numerator and today's denominator. Size without a head changes only the denominator to `managed-tokens`. While selecting, the numerator is the sink's sum, using the index count for a stored frame and `(length + 3) / 4`, minimum 1, for text that is not in the index yet.

**Reason.** Two counters would fill the bar against a different total than the transcript. The index count is the stored estimate. Text not yet stored has no index count, so the same heuristic covers it.

**Avoidances.** Do not count the canvas bar as a sum of index rows while it is still one render of the working set. Do not change the numerator when the size is set and no head is designated. Do not use a second formula beside `(length + 3) / 4` with empty equal to 1. Do not leave the denominator on the old window while `managed-tokens` is set.


**semantic-key:** PK-2420 PK-2421 PK-2422 PK-2423 PK-2424 PK-2425 PK-2426 PK-2427 PK-2428 PK-2429 PK-2430 PK-2431 PK-2432 PK-2433 PK-2434 PK-2435 PK-2436 PK-2437 PK-2438 PK-2439 PK-2440 PK-2441 PK-2442 PK-2443 PK-2444 PK-2445 PK-2446 PK-2447 PK-2448 PK-2449 PK-2450 PK-2451 PK-2452 PK-2453 PK-2454 PK-2455 PK-2456 PK-2457 PK-2458 PK-2459 PK-2460 PK-2461 PK-2462 PK-2463 PK-2464 PK-2465 PK-2466 PK-2467

## One cursor

The designated head is the only extra cursor on the terminal walk. Acceptance of this serial retires the clip-range floor. The clip-range page stays the filed history. A build does not stack a floor sequence on top of the designated head.

The clip-range page proposes a floor sequence on `windowIds`. The tree has no floor field. The sink does not read `clip.bin` and does not take a floor sequence. `windowIds` keeps the signature it has. The floor is retired. The clip-range page stays the filed history.

**Truth.** One extra cursor, the designated head. No floor sequence. `windowIds` keeps its signature. `clip.bin` is not read.

**Reason.** A floor plus a head is two cursors deciding the same window. The signature of `windowIds` already has callers. Adding a floor argument would be the second cursor.

**Avoidances.** Do not stack a floor sequence on the designated head. Do not read `clip.bin` from the sink. Do not change the `windowIds` signature. Do not delete the clip-range page.


**semantic-key:** PK-2468 PK-2469 PK-2470 PK-2471 PK-2472 PK-2473 PK-2474 PK-2475 PK-2476 PK-2477 PK-2478 PK-2479 PK-2480 PK-2481 PK-2482 PK-2483 PK-2484 PK-2485 PK-2486 PK-2487 PK-2488 PK-2489 PK-2490 PK-2491 PK-2492 PK-2493 PK-2494 PK-2495 PK-2496 PK-2497 PK-2498 PK-2499 PK-2500 PK-2501 PK-2502 PK-2503 PK-2504 PK-2505 PK-2506 PK-2507 PK-2508 PK-2509 PK-2510 PK-2511 PK-2512 PK-2513 PK-2514

## Page spill

A page moves into the pack when the next frame will not fit, and the sink does not start a second spill.

The byte mover stays the page-full publish into `pages.bin`. The sink does not slide bytes toward a barrier inside a page. It does not spill a selected span of the window as its own file.

**Truth.** Page-full publish remains the only spill. The sink selects; it does not move bytes.

**Reason.** A second spill inside the page would be a compactor with a different address rule. The sequence is the return address, so the window does not need its own byte motion.

**Avoidances.** Do not start a sink-owned spill. Do not slide frames inside a page toward a barrier. Do not treat wire omission as a page publish.


**semantic-key:** PK-2515 PK-2516 PK-2517 PK-2518 PK-2519 PK-2520 PK-2521 PK-2522 PK-2523 PK-2524 PK-2525 PK-2526 PK-2527 PK-2528 PK-2529 PK-2530 PK-2531 PK-2532 PK-2533 PK-2534 PK-2535 PK-2536 PK-2537 PK-2538 PK-2539 PK-2540 PK-2541 PK-2542 PK-2543 PK-2544 PK-2545 PK-2546 PK-2547 PK-2548 PK-2549 PK-2550 PK-2551 PK-2552 PK-2553 PK-2554 PK-2555 PK-2556 PK-2557 PK-2558 PK-2559 PK-2560 PK-2561

## Prior bytes

The previous bytes of an edited sequence stay in the pack.

The spill of an edit is relocation of the index pointer, not deletion of the old bytes.

**Truth.** Prior bytes remain in the pack after an edit. The live row no longer names them.

**Reason.** Deleting the old span would be compaction. The pack has no free list, and the next frame is written at the end of the used stretch.

**Avoidances.** Do not delete the previous bytes. Do not reuse the old offset for the new frame. Do not compact the hole the edit left.


**semantic-key:** PK-2562 PK-2563 PK-2564 PK-2565 PK-2566 PK-2567 PK-2568 PK-2569 PK-2570 PK-2571 PK-2572 PK-2573 PK-2574 PK-2575 PK-2576 PK-2577 PK-2578 PK-2579 PK-2580 PK-2581 PK-2582 PK-2583 PK-2584 PK-2585 PK-2586 PK-2587 PK-2588 PK-2589 PK-2590 PK-2591 PK-2592 PK-2593 PK-2594 PK-2595 PK-2596 PK-2597 PK-2598 PK-2599 PK-2600 PK-2601 PK-2602 PK-2603 PK-2604 PK-2605 PK-2606 PK-2607 PK-2608

## Provider window

The provider window stays a config fact, and the operator sets the managed size so it falls inside that allocation.

The size of the provider window is a factor in configuration. The operator's managed size typically falls within the provider's token allocation.

**Truth.** The provider window remains a config fact. The managed size is chosen inside it.

**Reason.** The sink cannot see a provider allocation it was not given. The operator's size has to be the smaller one or the provider still clips.

**Avoidances.** Do not replace the provider window with the managed size. Do not set the managed size outside that allocation and expect the sink to hide the clip.


**semantic-key:** PK-2609 PK-2610 PK-2611 PK-2612 PK-2613 PK-2614 PK-2615 PK-2616 PK-2617 PK-2618 PK-2619 PK-2620 PK-2621 PK-2622 PK-2623 PK-2624 PK-2625 PK-2626 PK-2627 PK-2628 PK-2629 PK-2630 PK-2631 PK-2632 PK-2633 PK-2634 PK-2635 PK-2636 PK-2637 PK-2638 PK-2639 PK-2640 PK-2641 PK-2642 PK-2643 PK-2644 PK-2645 PK-2646 PK-2647 PK-2648 PK-2649 PK-2650 PK-2651 PK-2652 PK-2653 PK-2654 PK-2655 PK-2656

## Rust bar

The Rust inspector paints from a client-only reading of the same selection, and the Rust client still sends the working set it sends today.

The host reports the managed size on the session facts both clients already read. The Rust heat bar uses the report. A settings key that reader drops is not the report. The reader keeps the context windows it already keeps and drops every other key. It reads `num-ctx` from the session facts. A private function beside `paint_inspector` reads the working set the canvas already holds and paints the inspector in the same order the host class runs: the system pin, the designated head in ascending order, the live line charged before the historical tail, then whole tail atoms, stopping at the first atom that does not fit. The paint stays newest first. The yellow mood row stays first. The cards stay the full vault. While the size is absent or `0`, or while no head is designated, the inspector keeps the list it paints today. The same reading supplies the numerator while the sink is selecting, so the bar and the inspector count one transcript. `managed-tokens` stays on the session facts beside `num-ctx`. `head-count` rides those same facts. Absent or `0` means unset, and the bar keeps the window it uses today. A value above zero is the denominator. The working set that leaves the client stays that working set. The host runs `ReichenbachSink` on it. The terminal tape, the index counts, and `head.yaml` stay on the host walk. `message list` stays the full stored tape. The note on that private function says it borrows the idea from the host class `ReichenbachSink`. The name stays on the host class. The Rust client does not read a new settings key and does not gain the type. The host's list is the wire. One tape is what a check of this seat compares.

**Truth.** The Rust client borrows the selection in a private function beside `paint_inspector`, uses `managed-tokens` and `head-count` from the session facts, and still sends today's working set. It does not gain the type.

**Reason.** The host list is the wire. A client that trimmed would make the host trim a different set than the operator saw. A settings key the reader drops would never arrive, so the size has to ride the session facts beside `num-ctx`.

**Avoidances.** Do not put `ReichenbachSink` on the Rust client. Do not add a settings key for the size or the count. Do not trim the working set before it leaves the client. Do not paint a different order than pin, ascending head, live line, then whole tail atoms. Do not change the inspector while the size is absent or `0`, or while no head is designated. Do not move `head.yaml` or the index counts off the host.


**semantic-key:** PK-2657 PK-2658 PK-2659 PK-2660 PK-2661 PK-2662 PK-2663 PK-2664 PK-2665 PK-2666 PK-2667 PK-2668 PK-2669 PK-2670 PK-2671 PK-2672 PK-2673 PK-2674 PK-2675 PK-2676 PK-2677 PK-2678 PK-2679 PK-2680 PK-2681 PK-2682 PK-2683 PK-2684 PK-2685 PK-2686 PK-2687 PK-2688 PK-2689 PK-2690 PK-2691 PK-2692 PK-2693 PK-2694 PK-2695 PK-2696 PK-2697 PK-2698 PK-2699 PK-2700 PK-2701 PK-2702 PK-2703

## Side file

On a disk terminal region the head count and the designated sequences live in `head.yaml` beside `manifest.bin`. A RAM region keeps those fields in memory for the life of the region. The file is not `clip.bin`. `manifest.bin` stays 112 bytes.

`RegionManifest.SIZE` is 112. A 64-byte `clip.bin` whose head origin is one long cannot hold the designated sequences. The head file is version 1, with `count` and `seqs`. A missing file, a version other than 1, a count of 0, and an empty list mean no head. A count above zero with an empty list means no head. A list is the locked prefix, in sequence order, with no hole. A shorter prefix than the count is still a head. The file's `count` is the same integer as `head-count`. The server fact is the count the client reads on connect. The write uses a temporary file and a move. A version other than 1 is left unread. A RAM region keeps the same two fields in memory and drops them with the region. It does not write the file.

**Truth.** `head.yaml` version 1, fields `count` and `seqs`, sits beside the 112-byte `manifest.bin`. Empty, missing, foreign version, or count 0 is no head. The list is a packed prefix. RAM keeps the same fields and writes no file.

**Reason.** Designated sequences do not fit in one long inside a 64-byte clip file. Growing the manifest would change every pack. A foreign version read as version 1 would lock turns the file did not name.

**Avoidances.** Do not store the head in `clip.bin`. Do not grow `manifest.bin`. Do not read a version other than 1. Do not treat an empty list as a head. Do not treat a count above zero with an empty list as a head. Do not write `head.yaml` for a RAM region. Do not put a hole in `seqs`.


**semantic-key:** PK-2704 PK-2705 PK-2706 PK-2707 PK-2708 PK-2709 PK-2710 PK-2711 PK-2712 PK-2713 PK-2714 PK-2715 PK-2716 PK-2717 PK-2718 PK-2719 PK-2720 PK-2721 PK-2722 PK-2723 PK-2724 PK-2725 PK-2726 PK-2727 PK-2728 PK-2729 PK-2730 PK-2731 PK-2732 PK-2733 PK-2734 PK-2735 PK-2736 PK-2737 PK-2738 PK-2739 PK-2740 PK-2741 PK-2742 PK-2743 PK-2744 PK-2745 PK-2746 PK-2747 PK-2748 PK-2749 PK-2750 PK-2751

## Sovereign tape

The operator owns the tape. An older turn enters the conversation when the operator adds it.

The operator controls, at each step, what goes into the conversation. That control is why compaction is unnecessary.

**Truth.** Older material enters only by an explicit add. The operator owns the tape.

**Reason.** If the store summarized or dropped history on its own, the operator would no longer decide the conversation.

**Avoidances.** Do not insert an older turn unless the operator adds it. Do not compact because an older turn was not added.


**semantic-key:** PK-2752 PK-2753 PK-2754 PK-2755 PK-2756 PK-2757 PK-2758 PK-2759 PK-2760 PK-2761 PK-2762 PK-2763 PK-2764 PK-2765 PK-2766 PK-2767 PK-2768 PK-2769 PK-2770 PK-2771 PK-2772 PK-2773 PK-2774 PK-2775 PK-2776 PK-2777 PK-2778 PK-2779 PK-2780 PK-2781 PK-2782 PK-2783 PK-2784 PK-2785 PK-2786 PK-2787 PK-2788 PK-2789 PK-2790 PK-2791 PK-2792 PK-2793 PK-2794 PK-2795 PK-2796 PK-2797 PK-2798

## SSH door

On the terminal tape, a turn the sink leaves off the wire stays in the region, and the pack persists it.

The omitted turn is not deleted. Persistence is the pack the region already has.

**Truth.** A terminal turn omitted from the wire remains in the region and is persisted with the pack.

**Reason.** The operator can still list and show that turn. Deleting it would make the wire selection a deletion.

**Avoidances.** Do not delete a terminal turn because it missed the wire. Do not persist it anywhere but the region pack.


**semantic-key:** PK-2799 PK-2800 PK-2801 PK-2802 PK-2803 PK-2804 PK-2805 PK-2806 PK-2807 PK-2808 PK-2809 PK-2810 PK-2811 PK-2812 PK-2813 PK-2814 PK-2815 PK-2816 PK-2817 PK-2818 PK-2819 PK-2820 PK-2821 PK-2822 PK-2823 PK-2824 PK-2825 PK-2826 PK-2827 PK-2828 PK-2829 PK-2830 PK-2831 PK-2832 PK-2833 PK-2834 PK-2835 PK-2836 PK-2837 PK-2838 PK-2839 PK-2840 PK-2841 PK-2842 PK-2843 PK-2844 PK-2845

## SSH lock

`message lock <seq>` locks the next open sequence in the eligible run, and `message unlock <seq>` releases the last locked sequence. Zero locks means that terminal send has no head.

`message list`, `messages`, `message show`, and `message find` stay the console verbs already in the tree. `message list` and `messages` still list every stored turn. A row in the eligible run shows that it may lock, and a locked row shows the lock. The eligible run is the first `head-count` non-system turns, in sequence order. A system turn stays in the pin and is not eligible. One sequence spends one of the count. `message lock <seq>` locks the first eligible sequence that is still open. `message unlock <seq>` releases the last locked sequence. `message edit` changes the text and leaves the lock as it is. A new turn becomes eligible only when it sits inside that eligible run. The header text is written there by the conversation or by `message edit`, and then locked. A shorter prefix than the count is still a head. Zero locks means that send has no head, and the walk stays the one it uses today. A miss returns a yellow error and changes nothing: a missing sequence, a system turn, a sequence outside the eligible run, a lock that would skip ahead, or an unlock that would leave a hole. The locked sequences ride in ascending order after the pin. The sequence list in `head.yaml` is that prefix. An empty list means no head.

**Truth.** Lock grows the packed prefix by the next open eligible sequence. Unlock shrinks it from the end. A miss is a yellow error and changes nothing. Empty means no head.

**Reason.** A lock that could skip ahead would open a hole, and a hole would put a middle sequence on the wire. Unlocking from the middle would do the same. The verbs are new because list, show, and find already have meanings.

**Avoidances.** Do not lock a system turn. Do not lock past the eligible run. Do not skip ahead. Do not unlock a sequence that is not the last locked one. Do not change the pack on a miss. Do not clear the lock from `message edit`. Do not treat an empty `head.yaml` list as a head. Do not stop listing turns that are outside the prefix.


**semantic-key:** PK-2846 PK-2847 PK-2848 PK-2849 PK-2850 PK-2851 PK-2852 PK-2853 PK-2854 PK-2855 PK-2856 PK-2857 PK-2858 PK-2859 PK-2860 PK-2861 PK-2862 PK-2863 PK-2864 PK-2865 PK-2866 PK-2867 PK-2868 PK-2869 PK-2870 PK-2871 PK-2872 PK-2873 PK-2874 PK-2875 PK-2876 PK-2877 PK-2878 PK-2879 PK-2880 PK-2881 PK-2882 PK-2883 PK-2884 PK-2885 PK-2886 PK-2887 PK-2888 PK-2889 PK-2890 PK-2891 PK-2892

## System pin

When the sink selects, the leading system run stays pinned the way `windowIds` pins it today. Those rows are not head turns. Their tokens are spent before the tail budget. The pin rides with the head when the two together exceed the managed size.

`windowIds` pins the leading system run, or the last system message when that run is empty. Pinned rows stay out of the non-system cap. If the pins already exceed `max-tokens`, the unset walk still takes the first tail atom. The sink calls that same pin first. Those tokens are spent before the head. A designated system sequence is already in the pin and is not taken a second time. When the pin and the head together exceed the managed size, the historical tail stays empty, the live line still rides, and the console says the head is over the allowance.

**Truth.** The sink pins the leading system run, or the last system message if that run is empty, spends those tokens before the head, and does not take a system sequence twice.

**Reason.** The standing system text is part of the frame. Leaving it to the tail budget would drop it in favor of a newer user turn. Spending it twice would shrink the tail without showing two copies.

**Avoidances.** Do not count pinned system rows as head turns. Do not spend pin tokens after the tail. Do not designate a system sequence a second time inside the head. Do not drop the live line when the pin and the head exceed the size. Do not omit the console sentence in that case.


**semantic-key:** PK-2893 PK-2894 PK-2895 PK-2896 PK-2897 PK-2898 PK-2899 PK-2900 PK-2901 PK-2902 PK-2903 PK-2904 PK-2905 PK-2906 PK-2907 PK-2908 PK-2909 PK-2910 PK-2911 PK-2912 PK-2913 PK-2914 PK-2915 PK-2916 PK-2917 PK-2918 PK-2919 PK-2920 PK-2921 PK-2922 PK-2923 PK-2924 PK-2925 PK-2926 PK-2927 PK-2928 PK-2929 PK-2930 PK-2931 PK-2932 PK-2933 PK-2934 PK-2935 PK-2936 PK-2937 PK-2938 PK-2939

## Tail budget

The remainder of the managed size, after the head's token width, is the token budget that selects turns from the tail upward for the wire.

That remaining allocated space is what decides which turns, taken from the tail upward, are included in the send.

**Truth.** Tail budget equals managed size minus the head's token width, spent from the tail upward.

**Reason.** The head is already committed. Only the remainder can select more turns, and the newest tail is the relevant edge.

**Avoidances.** Do not spend the head's width a second time against the tail. Do not select tail turns from the oldest end first. Do not treat this subtraction as a second managed size.


**semantic-key:** PK-2940 PK-2941 PK-2942 PK-2943 PK-2944 PK-2945 PK-2946 PK-2947 PK-2948 PK-2949 PK-2950 PK-2951 PK-2952 PK-2953 PK-2954 PK-2955 PK-2956 PK-2957 PK-2958 PK-2959 PK-2960 PK-2961 PK-2962 PK-2963 PK-2964 PK-2965 PK-2966 PK-2967 PK-2968 PK-2969 PK-2970 PK-2971 PK-2972 PK-2973 PK-2974 PK-2975 PK-2976 PK-2977 PK-2978 PK-2979 PK-2980 PK-2981 PK-2982 PK-2983 PK-2984 PK-2985 PK-2986

## Tail fit

A tail atom is the atom `windowIds` already keeps: an assistant frame and the user, tool, or tool-call frame in front of it. An atom that does not fit stays off the wire. The unset walk still includes the atom that crosses `max-tokens`.

The unset walk adds the atom, then stops. The first non-system atom is kept even when it is already over the token cap. A budget of 20 with four 10-token notes keeps three notes, the sequences that sum to the cap and the crossing note the unset rule includes. The sink's tail walk uses that same atom and spends what remains after the pin, the head, and the live line. An atom is added only when the whole atom fits. The walk stops at the first atom that does not. Today's `windowIds` stays the path when the sink is not selecting, and that path still includes the crossing atom.

**Truth.** While the sink selects, a tail atom is added only when the whole atom fits, and the walk stops at the first atom that does not. The unset walk still includes the crossing atom.

**Reason.** Including a crossing atom on the managed path is how the provider ends up clipping. The unset path keeps that inclusion because that is the walk already in the tree, including the case of a budget of 20 and four 10-token notes.

**Avoidances.** Do not include a partial tail atom while the sink is selecting. Do not change the unset walk to drop the crossing atom. Do not split the assistant frame from the user, tool, or tool-call frame in front of it.


**semantic-key:** PK-2987 PK-2988 PK-2989 PK-2990 PK-2991 PK-2992 PK-2993 PK-2994 PK-2995 PK-2996 PK-2997 PK-2998 PK-2999 PK-3000 PK-3001 PK-3002 PK-3003 PK-3004 PK-3005 PK-3006 PK-3007 PK-3008 PK-3009 PK-3010 PK-3011 PK-3012 PK-3013 PK-3014 PK-3015 PK-3016 PK-3017 PK-3018 PK-3019 PK-3020 PK-3021 PK-3022 PK-3023 PK-3024 PK-3025 PK-3026 PK-3027 PK-3028 PK-3029 PK-3030 PK-3031 PK-3032 PK-3033

## Tail last

The tail is the last span the sink drops when the allowance is spent.

The tail remains live and is the last region considered for any omission.

**Truth.** When the allowance is spent, the tail is the last span omitted.

**Reason.** The tail is the live edge. Dropping it before the middle, or before an over-wide head is reported, hides the current turn.

**Avoidances.** Do not drop the tail before the middle. Do not drop the tail in order to shrink the designated head.


**semantic-key:** PK-3034 PK-3035 PK-3036 PK-3037 PK-3038 PK-3039 PK-3040 PK-3041 PK-3042 PK-3043 PK-3044 PK-3045 PK-3046 PK-3047 PK-3048 PK-3049 PK-3050 PK-3051 PK-3052 PK-3053 PK-3054 PK-3055 PK-3056 PK-3057 PK-3058 PK-3059 PK-3060 PK-3061 PK-3062 PK-3063 PK-3064 PK-3065 PK-3066 PK-3067 PK-3068 PK-3069 PK-3070 PK-3071 PK-3072 PK-3073 PK-3074 PK-3075 PK-3076 PK-3077 PK-3078 PK-3079 PK-3080

## Three surfaces

The inspector's turn list is the wire transcript. The yellow mood status row stays. The canvas cards still show every vault turn. `message list` and `messages` still list every stored turn. A turn the sink omitted is not an inspector row.

The inspector paints the kept working set, newest first, cut by `inspectorLineChars`. The yellow mood row is a separate first row. A mood debug view paints the invitation above the spliced user block when that invitation is in the transcript. The inspector calls `ReichenbachSink` and lists that transcript, newest first, still cut by `inspectorLineChars`. The canvas cards and `message list` do not call the sink.

**Truth.** The inspector lists the sink transcript, newest first, cut by `inspectorLineChars`, with the yellow mood row first. Cards and `message list` stay the full tape and do not call the sink.

**Reason.** The inspector is the wire. The cards and the list are the tape. One component feeds the inspector so the view cannot drift from the send.

**Avoidances.** Do not paint an omitted turn as an inspector row. Do not remove the yellow mood row. Do not call the sink from the canvas cards or from `message list`. Do not drop the `inspectorLineChars` cut. Do not paint a mood invitation that is not in the transcript.


**semantic-key:** PK-3081 PK-3082 PK-3083 PK-3084 PK-3085 PK-3086 PK-3087 PK-3088 PK-3089 PK-3090 PK-3091 PK-3092 PK-3093 PK-3094 PK-3095 PK-3096 PK-3097 PK-3098 PK-3099 PK-3100 PK-3101 PK-3102 PK-3103 PK-3104 PK-3105 PK-3106 PK-3107 PK-3108 PK-3109 PK-3110 PK-3111 PK-3112 PK-3113 PK-3114 PK-3115 PK-3116 PK-3117 PK-3118 PK-3119 PK-3120 PK-3121 PK-3122 PK-3123 PK-3124 PK-3125 PK-3126 PK-3127

## Reichenbach Sink

The sink is the component that chooses the transcript sent on the wire, and this page is the requirements and the shape, not a license to build.

It is not a page compactor and not a second memory engine. The token is `ReichenbachSink`. The state is finalized. A release-candidate pass was not required. A build waits for a stamped plan. The pre-plan is the sketch. The realization language is the host language. The other client stays. The page depends on the fall design, the paging specification, the version 1 requirements, reattach, clip range, and the page-full spill account.

The tape is the region. It stays append-only. In the terminal, `message list` and `messages` still list the whole conversation. `message show <seq>` still opens one turn, including a turn the provider will never see. Opening a sequence is rolling the tape. A new turn still appends at the live end. What was before that sequence, and what is after it, stays on the tape.

The sink is the basin that chooses the wire. The lip is the line under the locked head. The transcript is the head concatenated with the tail that still fits. Every sequence between those two has fallen past the lip. It stays listed. It stays showable. It stays off the wire. The host owns that size. The operator keeps the managed window smaller than the provider context, so the provider is never the one who clips.

The head is a count of turns the operator designates. Those turns stay on every wire until the operator changes them. The pager has no writer there. The change is a console edit of one sequence. The revised text is a new frame. The index row for that sequence follows the new frame. The list keeps one row, and that row shows the current text. The previous bytes stay in the pack.

Page spill stays the motion that moves bytes. A hot page still moves into `pages.bin` when the next frame will not fit. The fall does not start a second spill, and it does not slide bytes toward a barrier inside a page. The return address is the sequence.

The component name stays Reichenbach Sink. The sink is the basin. A window name would sit on `windowIds`. A fall name is the motion, and the motion is the selection. The first sentence under each heading is the law. The headings bind a later build. This page does not license the code. The stamped plan does. Until that build lands, the tree stays on today's `windowIds`.

**Truth.** The sink selects a locked head plus a fitting tail from an append-only tape. Page spill stays the only byte mover. A build waits for a stamped plan.

**Reason.** Models attend the head and the tail. Locking invariants in a fixed head and keeping the tail as the live edge removes any need to compact. The sequence is enough to return to a turn the wire omitted.

**Avoidances.** Do not compact the pack from this page. Do not start a second spill inside a page. Do not build before the plan is stamped. Do not rename the component onto `windowIds`. Do not drop a listed turn just because it is off the wire.


**semantic-key:** PK-3128 PK-3129 PK-3130 PK-3131 PK-3132 PK-3133 PK-3134 PK-3135 PK-3136 PK-3137 PK-3138 PK-3139 PK-3140 PK-3141 PK-3142 PK-3143 PK-3144 PK-3145 PK-3146 PK-3147 PK-3148 PK-3149 PK-3150 PK-3151 PK-3152 PK-3153 PK-3154 PK-3155 PK-3156 PK-3157 PK-3158 PK-3159 PK-3160 PK-3161 PK-3162 PK-3163 PK-3164 PK-3165 PK-3166 PK-3167 PK-3168 PK-3169 PK-3170 PK-3171 PK-3172 PK-3173 PK-3174 PK-3175

## Two call sites

The terminal sink runs inside the walk `ChatSession.buildPrompt` already uses, and the canvas sink runs on the host list that `holdRoux` stores and the provider receives, so both clients share one trim.

The prompt is built through `windowIds`. The facade sends the assembled list, and that call does not read `windowIds`. One class, `ReichenbachSink`, is called from `buildPrompt` for the terminal tape and from the facade on the assembled list before `holdRoux` and before the provider call. Both clients keep sending the working set they send today. The host trims. The sink selects only when the managed size is set and a head is designated. Otherwise each caller keeps the walk it has today.

**Truth.** `buildPrompt` and the host list before `holdRoux` are the two call sites. Both clients send today's working set. The host trims only when size and head are both set.

**Reason.** The facade does not read `windowIds`, so a sink that lived only inside that walk would never see the canvas push.

**Avoidances.** Do not trim on the client. Do not expect the facade to call `windowIds`. Do not select when the size is unset or no head is designated. Do not add a third call site.


**semantic-key:** PK-3176 PK-3177 PK-3178 PK-3179 PK-3180 PK-3181 PK-3182 PK-3183 PK-3184 PK-3185 PK-3186 PK-3187 PK-3188 PK-3189 PK-3190 PK-3191 PK-3192 PK-3193 PK-3194 PK-3195 PK-3196 PK-3197 PK-3198 PK-3199 PK-3200 PK-3201 PK-3202 PK-3203 PK-3204 PK-3205 PK-3206 PK-3207 PK-3208 PK-3209 PK-3210 PK-3211 PK-3212 PK-3213 PK-3214 PK-3215 PK-3216 PK-3217 PK-3218 PK-3219 PK-3220 PK-3221 PK-3222

## Two doors

One sink serves the terminal tape and the canvas push.

Both doors use the same selection.

**Truth.** The terminal tape and the canvas push share one sink.

**Reason.** Two selectors would drift on pin order, head order, and the atom that does not fit.

**Avoidances.** Do not build a second sink for the canvas. Do not build a second sink for the terminal.


**semantic-key:** PK-3223 PK-3224 PK-3225 PK-3226 PK-3227 PK-3228 PK-3229 PK-3230 PK-3231 PK-3232 PK-3233 PK-3234 PK-3235 PK-3236 PK-3237 PK-3238 PK-3239 PK-3240 PK-3241 PK-3242 PK-3243 PK-3244 PK-3245 PK-3246 PK-3247 PK-3248 PK-3249 PK-3250 PK-3251 PK-3252 PK-3253 PK-3254 PK-3255 PK-3256 PK-3257 PK-3258 PK-3259 PK-3260 PK-3261 PK-3262 PK-3263 PK-3264 PK-3265 PK-3266 PK-3267 PK-3268 PK-3269

## Two regions

The terminal tape is the region the session opens. A canvas push writes `user.aetheris` and replaces that region. The sink does not clear the terminal region. A canvas trim does not delete terminal turns.

The session opens a UUID region for the terminal tape. The push region is `user.aetheris`. `holdRoux` clears `user.aetheris` and adds the prompt. `holdRoux` keeps clearing only `user.aetheris`. The canvas sink drops omitted turns from the list before that add, so those turns are never written there. The terminal sink is a read filter on its region. The turns stay in that region.

**Truth.** `holdRoux` clears only `user.aetheris`. The terminal sink is a read filter. A canvas trim does not delete terminal turns.

**Reason.** The push region is replaced on every push. Clearing the terminal region there would drop the persisted tape. Writing omitted canvas turns into `user.aetheris` would put them on the provider path.

**Avoidances.** Do not clear the terminal region from the sink or from `holdRoux`. Do not write omitted canvas turns into `user.aetheris`. Do not treat `user.aetheris` as the terminal pack.


**semantic-key:** PK-3270 PK-3271 PK-3272 PK-3273 PK-3274 PK-3275 PK-3276 PK-3277 PK-3278 PK-3279 PK-3280 PK-3281 PK-3282 PK-3283 PK-3284 PK-3285 PK-3286 PK-3287 PK-3288 PK-3289 PK-3290 PK-3291 PK-3292 PK-3293 PK-3294 PK-3295 PK-3296 PK-3297 PK-3298 PK-3299 PK-3300 PK-3301 PK-3302 PK-3303 PK-3304 PK-3305 PK-3306 PK-3307 PK-3308 PK-3309 PK-3310 PK-3311 PK-3312 PK-3313 PK-3314 PK-3315 PK-3316 PK-3317

## Unset size

When the managed size is unset, the heat bars keep today's budgets and the terminal walk keeps today's `windowIds`. A set size is the budget the heat bars use, and it is the token cap the sink spends.

The canvas bar uses `selectedHeatBudget`, the model window. The terminal bar uses 131072 when the engine word contains `ollama`, and 0 otherwise. The other client's terminal bar uses session `num-ctx`. `chat.max-tokens` is 0. Absent or `0` means unset, and the sink does not select. The bars keep those budgets. `buildPrompt` keeps today's `windowIds`. A set size is the line `managed-tokens` on the session facts the host already sends beside `num-ctx`. Both decoders ignore a line they do not know, so an older client still seats. A value above zero is the denominator. It is the token cap only when a head is also designated.

**Truth.** Absent or `0` leaves the sink off and leaves today's denominators: the model window, 131072 for an `ollama` engine word, 0 otherwise, and session `num-ctx` on the other terminal. `managed-tokens` above zero is the denominator, and it caps tokens only together with a designated head.

**Reason.** An unknown fact line must not break an older client. A size with no head would change the walk without a lock to protect. A head with no size would have no allowance to spend.

**Avoidances.** Do not select when `managed-tokens` is absent or `0`. Do not fail a decoder on an unknown fact line. Do not replace 131072, the model window, or session `num-ctx` while the size is unset. Do not treat a set size as a token cap when no head is designated.


**semantic-key:** PK-3318 PK-3319 PK-3320 PK-3321 PK-3322 PK-3323 PK-3324 PK-3325 PK-3326 PK-3327 PK-3328 PK-3329 PK-3330 PK-3331 PK-3332 PK-3333 PK-3334 PK-3335 PK-3336 PK-3337 PK-3338 PK-3339 PK-3340 PK-3341 PK-3342 PK-3343 PK-3344 PK-3345 PK-3346 PK-3347 PK-3348 PK-3349 PK-3350 PK-3351 PK-3352 PK-3353 PK-3354 PK-3355 PK-3356 PK-3357 PK-3358 PK-3359 PK-3360 PK-3361 PK-3362 PK-3363 PK-3364

## Unset walk

With no head designated, `windowIds` walks as the tree walks it today.

`RouxEngine.windowIds` is that walk. The sink does not replace it until a head is designated.

**Truth.** No designated head means today's `windowIds` walk, unchanged.

**Reason.** The sink is an addition for a locked head. Turning it on with nothing locked would change every existing prompt.

**Avoidances.** Do not select when no head is designated. Do not change the unset walk's atom rule. Do not require a head file in order to prompt.


**semantic-key:** PK-3365 PK-3366 PK-3367 PK-3368 PK-3369 PK-3370 PK-3371 PK-3372 PK-3373 PK-3374 PK-3375 PK-3376 PK-3377 PK-3378 PK-3379 PK-3380 PK-3381 PK-3382 PK-3383 PK-3384 PK-3385 PK-3386 PK-3387 PK-3388 PK-3389 PK-3390 PK-3391 PK-3392 PK-3393 PK-3394 PK-3395 PK-3396 PK-3397 PK-3398 PK-3399 PK-3400 PK-3401 PK-3402 PK-3403 PK-3404 PK-3405 PK-3406 PK-3407 PK-3408 PK-3409 PK-3410 PK-3411

## Working set

The sink trims turns that are already in the working set. Excluded stays out. Input only and Output only stay one half. A locked designation does not override those marks, and it does not put an excluded turn on the wire.

The card letters are Included, Excluded, Input only, and Output only. The pin writes Excluded. The locked head is not one of those letters. The client applies the letters before the turn enters the working set, as it does today. A locked cell that was excluded never enters the set. The host sees only the turns that arrived. A turn may carry `head: true`. Absent means the turn is not designated. Today's decoder ignores that line.

**Truth.** Letters are applied before the working set. Excluded never enters, even if locked. `head: true` may ride on a turn that arrived. A missing line means not designated, and an unknown line is ignored.

**Reason.** The host cannot see a turn the client never sent. Overriding Excluded with a lock would put a turn on the wire that the letter had removed.

**Avoidances.** Do not treat the lock as a fifth letter. Do not let a lock override Excluded, Input only, or Output only. Do not send an excluded turn. Do not fail the decoder on `head: true` or on its absence.


**semantic-key:** PK-3412 PK-3413 PK-3414 PK-3415 PK-3416 PK-3417 PK-3418 PK-3419 PK-3420 PK-3421 PK-3422 PK-3423 PK-3424 PK-3425 PK-3426 PK-3427 PK-3428 PK-3429 PK-3430 PK-3431 PK-3432 PK-3433 PK-3434 PK-3435 PK-3436 PK-3437 PK-3438 PK-3439 PK-3440 PK-3441 PK-3442 PK-3443 PK-3444 PK-3445 PK-3446 PK-3447 PK-3448 PK-3449 PK-3450 PK-3451 PK-3452 PK-3453 PK-3454 PK-3455 PK-3456 PK-3457 PK-3458

## `select-model` at the console prompt, and Select model on the console menu, open Model Config held on Ollama for the shell.

The model is chosen from the VT100 console, inside the command set that already runs there. `select-model` at `cmd>` opens the Model Config modal. A right-click on the console offers Select model and opens that same modal. The modal was built once so a later context could use it. This sentence names the full cycle and the build. This rule is closed. The plan stamp names the build.

The command name is `select-model`. It is registered with the other console commands. `help` lists it. The two-word forms `select model` and `list models` stay tabled. The menu label is Select model. It sits on the console menu that already holds Import.

The modal is the Model Config modal already on the cell. A console open uses that dialog. The family control stays on the dialog and is held on Ollama. Grok and Pool stay the cell lanes. A console open does not write a cell, a Grok settings row, or a pool flag. Thinking and effort on the Ollama pane write this console. A cell open stays the cell modal path.

`select-model` runs on the server. It writes one private OSC on the VT100 stream, then the line `Model Config`. The client removes that OSC before the terminal paints it and opens the modal with the shell model and the shell think string. A raw SSH session shows the line. It has no modal. The menu item, when the session is not muxed, leaves the modal closed and the status line says the raw console has no modal.

A menu click on a muxed session asks the server for the shell model on the existing Scope A data frame. The first line is `shell_model:`. The reply is `shell_model_result:`. A change in the modal sends `shell_model_set:` on that same frame. Both are recognized before a cell execute, so they do not take an execute slot and they do not mark a cell busy. There is no new channel and no new wire type. The cell id on these frames is nil.

The shell stores the model name it already stored, and a think string beside it. The next console chat turn sends `ollama/` plus that model name, and that think string. An empty model name keeps the configured model. A turn already inside the call keeps the name it started with. `swap` and `ollama load` stay the weight movers. This click does not POST to Ollama and does not unload a model. The status key `model` stays. After a set, the console prints `model` and the id.

One modal flag. A second open while the dialog is up does nothing. The menu tip is written in this same turn. It names the console, the Ollama id, Thinking, and that Grok and Pool stay on the cell. The new public type this build names is `SelectModelCommand`. The new methods this build names are `CommandContext.getConsoleThink`, `AmwpClientListener.onConsoleModel`, and `ChatShell.applyConsoleModel`. No new package, vault column, G-key, channel, or wire type.

The project compiles, and the test suite is not the proof. The window is not opened. The live POST is not made. A pool turn is not sent.

The command stays on the server and the dialog stays on the client. Grok and Pool stay on the cell. Select model sits beside Import. The shell model rides the data frame that already carries `show:` and `tags:`, and that model is put on the next console turn. The raw session and the nil cell id stay. The row is the build.

**Truth.** `select-model` is a console command that `help` lists. On a muxed session it opens Model Config held on Ollama and writes the shell model and think string. A raw session prints `Model Config` and has no modal. The frames are `shell_model:`, `shell_model_result:`, and `shell_model_set:` on the existing Scope A data frame, with a nil cell id. The new public type is `SelectModelCommand`.

**Reason.** The modal was built once so the console can use it without a second dialog. Grok and Pool stay cell lanes so a console open does not write a cell, a Grok settings row, or a pool flag. The private OSC is removed before paint so the terminal does not show the control sequence, and the following line `Model Config` is what a raw session can show. The frames are recognized before a cell execute so they do not take a slot or mark a cell busy. A turn already inside the call keeps the name it started with. `swap` and `ollama load` stay the weight movers, so this click does not POST and does not unload a model.

**Avoidances.** Do not take `select model` or `list models` off the table. Do not open a second modal while the dialog is up. Do not write a cell, a Grok settings row, or a pool flag from a console open. Do not give these frames a cell id. Do not add a channel or a wire type. Do not POST to Ollama or unload a model from this click. Do not open the window, make the live POST, or send a pool turn from this filing.


**semantic-key:** PK-3523 PK-3524 PK-3525 PK-3526 PK-3527 PK-3528 PK-3529 PK-3530 PK-3531 PK-3532 PK-3533 PK-3534 PK-3535 PK-3536 PK-3537 PK-3538 PK-3539 PK-3540 PK-3541 PK-3542 PK-3543 PK-3544 PK-3545 PK-3546

## `select-model` with `grok`, `ollama`, or `pool` switches only that family, and a bare command still opens the modal.

The model modal works from this client's VT100. A standard VT100 console has no modal. `select-model` in cmd mode takes one argument, `grok`, `ollama`, or `pool`, and switches that family only, using the options last stored for it. A bare `select-model` still opens the modal. This sentence names the full cycle and the build. This rule is closed. The plan stamp names the build.

The shortcut is the family word. It does not take a model id, a think word, or any other option. The stored options for that family stay. The command writes the model line. Any other argument is refused and does not open the modal.

A bare `select-model` keeps the OSC and the `Model Config` line. This client still opens the modal.

`select-model grok`, `select-model ollama`, and `select-model pool` set the console family to that word. The Ollama id, the Grok id, think, effort, send-effort, stream, temperature, max tokens, burner, and pool think stay as stored. The command writes `model` and the target label. It does not send the OSC.

Any other argument, including a second word, is refused with a usage line. The family stays. The modal stays closed.

The new method this build names is `CommandContext.selectConsoleFamily`. `ChatShell` implements it. No new package, public type, vault column, G-key, channel, or wire type.

The project compiles, and the test suite is not the proof. The window is not opened. The live POST is not made. A pool turn is not sent. A live SSH connect is not made.

The stored options stay, and a bad argument stays off the family. The modal path stays. The family switches and the model line is written. The three words and the nil cell stay. The row is the build.

**Truth.** Bare `select-model` still opens the modal through the OSC and the line `Model Config`. `select-model grok`, `select-model ollama`, and `select-model pool` switch the family, keep the stored options, write `model` and the target label, and do not send the OSC. Any other argument, including a second word, is a usage refusal.

**Reason.** A standard VT100 has no modal, so the family word is the switch. The word does not take a model id or a think word because the options last stored for that family stay. A bad argument must not change the family and must not open the modal.

**Avoidances.** Do not send the OSC when a family word is present. Do not change stored options on a family switch. Do not accept a model id, a think word, or a second word. Do not open the modal on a refused argument. Do not add a package, a public type, a vault column, a G-key, a channel, or a wire type. Do not open the window, make the live POST, send a pool turn, or make a live SSH connect from this filing.


**semantic-key:** PK-3553 PK-3554 PK-3555 PK-3556 PK-3557 PK-3558 PK-3559 PK-3560 PK-3561 PK-3562 PK-3563 PK-3564 PK-3565 PK-3566 PK-3567 PK-3568 PK-3569 PK-3570 PK-3571 PK-3572 PK-3573 PK-3574 PK-3575
